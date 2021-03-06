package com.jaxon.bowling.processor.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jaxon.bowling.exception.BusinessException;
import com.jaxon.bowling.model.dto.GameDTO;
import com.jaxon.bowling.reader.FileReader;

@Component
public class GameProcessorImpl{
	
	@Value("${file.processor.separator}")
	private String separator;
	
	@Value("${file.processor.faul}")
	private String faul;
	
	public List<GameDTO> read(String filename, Charset charset) {
		return transform(FileReader.read(filename, charset));
	}
	
	private List<GameDTO> transform(List<String> records) {
		int attempt=1;
		int frame=1;
		List<GameDTO> games= new ArrayList<>();
		String currentPlayer="";
		for (String r : records) {
			String[] s = r.split(Optional.ofNullable(separator).orElse(" ").equals("")?" ":Optional.ofNullable(separator).orElse(" "));
			this.validateFormat(s.length!=2, s);
			if(games.isEmpty()) {currentPlayer=s[0];}
			
			GameDTO game = new GameDTO();
			game.setPlayer(s[0]);
			game.setResult(Optional.ofNullable(faul).orElse("F").equalsIgnoreCase(s[1])?-1:this.getResult(s[0],s[1]));
			
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
	

	private List<GameDTO> getResponse(List<GameDTO> games,List<String> records){
		if(getNumberOfPlayers(games)!=1) {
			return games.stream().collect(Collectors.toList());
		}else {
			return this.processSinglePlayer(records).collect(Collectors.toList());
		}
	}
	

	private Stream<GameDTO> processSinglePlayer(List<String> records){
		int attempt=1;
		int frame=1;
		int countByFrame=1;
		List<GameDTO> games= new ArrayList<>();
		for (String r : records) {
			String[] s = r.split(Optional.ofNullable(separator).orElse(" ").equals("")?" ":Optional.ofNullable(separator).orElse(" "));
			this.validateFormat(s.length!=2, s);
			GameDTO game = new GameDTO();
			game.setPlayer(s[0]);
			game.setResult(Optional.ofNullable(faul).orElse("F").equalsIgnoreCase(s[1])?-1:this.getResult(s[0],s[1]));
			game.setFrame(frame);
			
			game.setAttempt(attempt);
			if((game.getResult()==10 || countByFrame==2) && frame<10) {
				attempt=1;
				countByFrame=1;
				frame++;
			}else {
				countByFrame++;
			}
			
			games.add(game);
			if(countByFrame!=1) {
				attempt++;
			}
		}
		return games.stream();
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

	private int getNumberOfPlayers(List<GameDTO> games) {
		return games.stream().collect(Collectors.groupingBy(GameDTO::getPlayer, Collectors.counting())).size();
	}

}
