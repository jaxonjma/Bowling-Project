package com.jaxon.bowling.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
	
	private static final int TOTAL_FRAMES =10;
	private static final int TOTAL_PINES  =10;
	private static final ToIntFunction<Game> VALIDATE_FAULT = gbpa-> gbpa.getResult()!=-1?gbpa.getResult():0;
	
	@Override
	@Transactional
	public boolean showGame(Long idProcess) {
		List<Game> games= gameRepository.findGamesByProcess(idProcess);
		this.validateAttemptsByFrame(games);
		this.validateTotalFrames(games);
		this.calculateTotalByFrame(games);
		this.save(games);
		return true;
	}
	
	private void calculateTotalByFrame(List<Game> games) {
		this.filterGames(games);
		games.stream().forEach(g->{
			if(g.getAttempt()>1) {
				return;
			}
			List<Game> gamesByPlayer=new ArrayList<>(games);
					   gamesByPlayer.removeIf(gbp-> !g.getPlayer().equals(gbp.getPlayer()));
			
			g.setTotalFrame(this.getTotalByFrame(g, gamesByPlayer));
		});
	}

	private int getTotalByFrame(Game g, List<Game> gamesByPlayer) {
		int frame = g.getFrame();
		int sumByFrame = this.sumFrame(gamesByPlayer, frame);
		if(sumByFrame<TOTAL_PINES || frame==TOTAL_FRAMES) {
			return sumByFrame+this.sumPreviousFrame(gamesByPlayer, frame);
		}else if(sumByFrame==TOTAL_PINES) {
			if(this.isStrike(gamesByPlayer, frame)) {
				return sumByFrame+this.sumPreviousFrame(gamesByPlayer, frame)+this.sumTwoNextFrames(gamesByPlayer, frame);
			}else {
				return sumByFrame+this.sumPreviousFrame(gamesByPlayer, frame)+this.sumNextFrame(gamesByPlayer, frame);
			}
		}else {
			throw new BusinessException(String.format("Frame with more than 10 points found for player %s frame %s", g.getPlayer(),g.getFrame()));
		}
		
	}
	
	private int sumTwoNextFrames(List<Game> gamesByPlayer, int frame) {
		return this.sumNextFrame(gamesByPlayer, frame)+this.getSecondAttendtInFrame(gamesByPlayer, frame<TOTAL_FRAMES?frame+1:frame);
	}
	
	private int getSecondAttendtInFrame(List<Game> gamesByPlayer, int frame) {
		Game g=gamesByPlayer.stream().filter(gbp->gbp.getFrame()==frame && gbp.getAttempt()==2).findFirst().orElse(null);
		if(g==null){
			final int f=frame +1;
			g = gamesByPlayer.stream().filter(gbp-> gbp.getFrame()==f).sorted(Comparator.comparingInt(Game::getAttempt)).findFirst().orElse(new Game());
		}
		return g.getResult()!=-1&&g.getResult()!=null?g.getResult():0;
	}
	
	private int sumNextFrame(List<Game> gamesByPlayer, int frame) {
		if(frame<TOTAL_FRAMES) {
			final int f = frame+1;
			return gamesByPlayer.stream().filter(gbp-> 
				   gbp.getFrame()==f && gbp.getAttempt()==1).mapToInt(VALIDATE_FAULT).sum();
		}else {
			return gamesByPlayer.stream().filter(gbp-> 
			   gbp.getFrame()==frame && gbp.getAttempt()==2).mapToInt(VALIDATE_FAULT).sum();
		}
	}
	
	private int sumPreviousFrame(List<Game> gamesByPlayer, int frame) {
		if(frame==1) {
			return 0;
		}else {
			final int f=frame-1;
			return gamesByPlayer.stream().filter(gbp-> f == gbp.getFrame()).mapToInt(g-> Optional.ofNullable(g.getTotalFrame()).orElse(0)).sum();
		}
	}
	
	private int sumFrame(List<Game> gamesByPlayer, int frame) {
		return gamesByPlayer.stream().filter(gbp-> frame == gbp.getFrame()).mapToInt(VALIDATE_FAULT).sum();
	}
	
	private boolean isStrike(List<Game> gamesByPlayer, int frame) {
		return gamesByPlayer.stream().filter(gbp-> frame == gbp.getFrame()).collect(Collectors.toList()).size()==1;
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
	
	//ajustar
	private void validateTotalFrames(List<Game> games) {
//		if(games.stream().filter(g-> TOTAL_FRAMES==g.getFrame()).count()!=2) {//CHANGE TO MORE PLAYERS
//			throw new BusinessException("Less than 10 plays found in the uploaded file!");
//		}
	}
	
	private void validateAttemptsAllowed(List<Game> filteredList, int allowedAmount, String player) {
		if(filteredList.size()>allowedAmount) {
			throw new BusinessException(String.format("More attempts than expected for player %s found!",player));
		}
	}
	
	private void filterGames(List<Game> games) {
//		List<Game> aux=new ArrayList<>(games);
//		games.removeIf(g-> (0==g.getResult()||-1==g.getResult()) && aux.stream().anyMatch(a-> g.getFrame()==a.getFrame() && TOTAL_PINES==a.getResult()));
	}
	
	private void save(List<Game> games) {
		games.stream().forEach(g->gameRepository.save(g));
	}
}
