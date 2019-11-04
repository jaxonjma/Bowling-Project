package com.jaxon.bowling.processor.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
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
	
	private Stream<Game> getResponse(List<Game> games,List<String> records){
		if(getNumberOfPlayers(games)!=1) {
			return games.stream();
		}else {
			return this.processSinglePlayer(records);
		}
	}
	
	private Stream<Game> transform(List<String> records) {
		int attempt=1;
		int frame=1;
		List<Game> games= new ArrayList<>();
		String currentPlayer="";
		for (String r : records) {
			String[] s = r.split(separator.equals("")?" ":separator);
			this.validateFormat(s.length!=2, s);
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
		return this.getResponse(games,records);
	}
	
	private int getCurrentValue(Game g) {
		return g.getResult()!=-1?g.getResult():0;
	}
	
	private void validateFormat(boolean e,String[] s) {
		if(e) {throw new BusinessException(String.format("A wrong formatted record was found! %s",String.join(",", s)));}
	}
	
	private Integer getResult(String player,String value) {
		Integer result = Integer.parseInt(value);
		if(result>=0) {
			return result;
		}else {
			throw new BusinessException(String.format("An invalid score was found for the player %s: %s",player,value));
		}
	}

	private int getNumberOfPlayers(List<Game> games) {
		return games.stream().collect(Collectors.groupingBy(Game::getPlayer, Collectors.counting())).size();
	}

	private Stream<Game> processSinglePlayer(List<String> records){
		int attempt=1;
		int frame=1;
		int preValue=0;
		List<Game> games= new ArrayList<>();
		for (String r : records) {
			String[] s = r.split(separator.equals("")?" ":separator);
			this.validateFormat(s.length!=2, s);
			
			Game game = new Game();
			game.setPlayer(s[0]);
			game.setResult(faul.equalsIgnoreCase(s[1])?-1:this.getResult(s[0],s[1]));
			
			if((game.getResult()==10 || preValue+this.getCurrentValue(game)==10) && frame<10) {
				attempt=1;
				int f=frame;
				if(games.stream().anyMatch(g-> s[0].equals(g.getPlayer()) && f==g.getFrame())) {
					frame++;
				}
			}
			game.setFrame(frame);
			game.setAttempt(attempt);
			
			games.add(game);
			preValue=game.getResult();
			attempt++;
		}
		return games.stream();
	}
}
