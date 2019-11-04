package com.jaxon.bowling.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaxon.bowling.exception.BusinessException;
import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.repository.IGameRepository;
import com.jaxon.bowling.service.GameService;

@Service
public class GameServiceImpl implements GameService{

	@Autowired
	private IGameRepository gameRepository;
	
	private static final int TOTAL_FRAMES=10;
	
	@Override
	public boolean showGame(Long idProcess) {
		List<Game> games= gameRepository.findGamesByProcess(idProcess);
		this.validateAttemptsByFrame(games);
		this.validateTotalFrames(games);
		this.calculateTotalByFrame(games);
		return true;
	}
	
	private void calculateTotalByFrame(List<Game> games) {
		int i=0;
		games.stream().forEach(g->{
			
		});
	}

	
	
	private void validateAttemptsByFrame(List<Game> games) {
		games.stream().forEach(g->{
			int frame=g.getFrame();
			String player=g.getPlayer();
			
			List<Game> auxGames=new ArrayList<>(games);
			List<Game> filteredList = auxGames.stream().filter(i-> frame==i.getFrame() && player.equals(i.getPlayer())).collect(Collectors.toList());
			if(frame<TOTAL_FRAMES) {
				validateAttemptsAllowed(filteredList, 2, player);
			}else if(frame==TOTAL_FRAMES) {
				validateAttemptsAllowed(filteredList, 3, player);
			}else {
				throw new BusinessException("More frames than expected have been found!");
			}
		});
	}
	
	private void validateTotalFrames(List<Game> games) {
		if(games.stream().filter(g-> TOTAL_FRAMES==g.getFrame()).count()!=2) {
			throw new BusinessException("Less than 10 plays found in the uploaded file!");
		}
	}
	
	private void validateAttemptsAllowed(List<Game> filteredList, int allowedAmount, String player) {
		if(filteredList.size()>allowedAmount) {
			throw new BusinessException(String.format("More attempts than expected for player %s found!",player));
		}
	}
}
