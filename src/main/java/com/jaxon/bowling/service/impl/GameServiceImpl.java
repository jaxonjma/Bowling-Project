package com.jaxon.bowling.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaxon.bowling.enums.States;
import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.model.dto.ResponseDTO;
import com.jaxon.bowling.printer.Printer;
import com.jaxon.bowling.repository.IGameRepository;
import com.jaxon.bowling.service.GameService;
import com.jaxon.bowling.util.GameUtil;

@Service
public class GameServiceImpl implements GameService{

	private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);
	
	@Autowired
	private IGameRepository gameRepository;
	
	@Autowired
	private Printer printer;
	
	private static final int TOTAL_FRAMES =10;
	private static final int TOTAL_PINES  =10;
	private static final ToIntFunction<Game> VALIDATE_FAULT = gbpa-> gbpa.getResult()!=-1?gbpa.getResult():0;
	
	@Override
	@Transactional
	public ResponseDTO showGame(Long idProcess) {
		List<Game> games= gameRepository.findGamesByProcess(idProcess);
		try {
			GameUtil.validateGames(games);
		} catch (Exception ex) {
			LOGGER.error("An error occurred while processing the file, please check the contents of the loaded file!");
			LOGGER.error(ex.getMessage(), ex);
			return new ResponseDTO(States.WARNING, ex.getMessage());
		}
		this.calculateTotalByFrame(games);
		this.save(games);
		return new ResponseDTO(States.LOADED,"");
	}
	
	@Override
	public void printResults(Long idProcess) {
		printer.printGames(gameRepository.findGamesByProcess(idProcess));
	}
	
	private void calculateTotalByFrame(List<Game> games) {
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
		}else {
			if(this.isStrike(gamesByPlayer, frame)) {
				return sumByFrame+this.sumPreviousFrame(gamesByPlayer, frame)+this.sumTwoNextFrames(gamesByPlayer, frame);
			}else {
				return sumByFrame+this.sumPreviousFrame(gamesByPlayer, frame)+this.sumNextFrame(gamesByPlayer, frame);
			}
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
		final int f = frame+1;
		return gamesByPlayer.stream().filter(gbp-> 
			   gbp.getFrame()==f && gbp.getAttempt()==1).mapToInt(VALIDATE_FAULT).sum();
		
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
	
	private void save(List<Game> games) {
		games.stream().forEach(g->gameRepository.save(g));
	}
	
}
