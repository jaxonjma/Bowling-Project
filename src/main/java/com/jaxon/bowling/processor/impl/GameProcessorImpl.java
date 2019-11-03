package com.jaxon.bowling.processor.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jaxon.bowling.exception.BusinessException;
import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.reader.FileReader;

@Component
public class GameProcessorImpl extends ProcessorImpl<Game>{
	
	@Value("${file.processor.separator}")
	private String separator;
	
	@Value("${file.processor.faul}")
	private String faul;
	
	@Override
	protected Stream<Game> read(Class<Game> clazz, String filename, Charset charset) {
		return transform(FileReader.read(filename, charset));
	}
	
	private Stream<Game> transform(List<String> records) {
		int attempt=1;
		int frame=1;
		List<Game> games= new ArrayList<>();
		String currentPlayer="";
		for (String r : records) {
			
			String[] s = r.split(separator.equals("")?" ":separator);
			
			if(s.length!=2) {throw new BusinessException(String.format("A wrong formatted record was found! %s",String.join(",", s)));}
			if(games.isEmpty()) {currentPlayer=s[0];}
			
			Game game = new Game();
			game.setPlayer(s[0]);
			game.setResult(faul.equalsIgnoreCase(s[1])?-1:this.getResult(s[0],s[1]));
			
			if(!currentPlayer.equals(s[0])) {
				currentPlayer=s[0];
				attempt=1;
				int f=frame;
				if(games.stream().anyMatch(g-> s[0].equals(g.getPlayer()) && f==g.getFrame())) {
					frame++;
				}
			}
			game.setFrame(frame);
			game.setAttempt(attempt);
			
			games.add(game);
			attempt++;
		}
		this.validateGames(games);
		return games.stream();
	}
	
	private Integer getResult(String player,String value) {
		Integer result = Integer.parseInt(value);
		if(result>=0) {
			return result;
		}else {
			throw new BusinessException(String.format("An invalid score was found for the player %s: %s",player,value));
		}
	}

	private void validateGames(List<Game> games) {
		Map<String, Long> countForPlayer = games.stream()
		        								.collect(Collectors.groupingBy(Game::getPlayer, Collectors.counting()));
		if(countForPlayer.size()!=2) {
			throw new BusinessException(String.format("The number of players is not as expected! Players found: %s",String.join(",", countForPlayer.keySet())));
		}
	}
}
