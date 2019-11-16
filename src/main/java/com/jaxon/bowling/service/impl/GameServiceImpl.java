package com.jaxon.bowling.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaxon.bowling.model.dto.GameDTO;
import com.jaxon.bowling.printer.Printer;
import com.jaxon.bowling.service.GameService;
import com.jaxon.bowling.util.GameUtil;

@Service
public class GameServiceImpl implements GameService{
	
	@Autowired
	private Printer printer;
	
	private static final int TOTAL_FRAMES =10;
	private static final int TOTAL_PINES  =10;
	private static final ToIntFunction<GameDTO> VALIDATE_FAULT = gbpa-> gbpa.getResult()!=-1?gbpa.getResult():0;
	
	@Override
	public void printResults(List<GameDTO> games) {
		GameUtil.validateGames(games);
		this.calculateTotalByFrame(games);
		printer.printGames(games);
	}
	
	private void calculateTotalByFrame(List<GameDTO> games) {
		games.stream().forEach(g->{
			if(g.getAttempt()>1) {
				return;
			}
			List<GameDTO> gamesByPlayer=new ArrayList<>(games);
					   gamesByPlayer.removeIf(gbp-> !g.getPlayer().equals(gbp.getPlayer()));
			
			g.setTotalFrame(this.getTotalByFrame(g, gamesByPlayer));
		});
	}

	private int getTotalByFrame(GameDTO g, List<GameDTO> gamesByPlayer) {
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
	
	private int sumTwoNextFrames(List<GameDTO> gamesByPlayer, int frame) {
		return this.sumNextFrame(gamesByPlayer, frame)+this.getSecondAttendtInFrame(gamesByPlayer, frame<TOTAL_FRAMES?frame+1:frame);
	}
	
	private int getSecondAttendtInFrame(List<GameDTO> gamesByPlayer, int frame) {
		GameDTO g=gamesByPlayer.stream().filter(gbp->gbp.getFrame()==frame && gbp.getAttempt()==2).findFirst().orElse(null);
		if(g==null){
			final int f=frame +1;
			g = gamesByPlayer.stream().filter(gbp-> gbp.getFrame()==f).sorted(Comparator.comparingInt(GameDTO::getAttempt)).findFirst().orElse(new GameDTO());
		}
		return g.getResult()!=-1&&g.getResult()!=null?g.getResult():0;
	}
	
	private int sumNextFrame(List<GameDTO> gamesByPlayer, int frame) {
		final int f = frame+1;
		return gamesByPlayer.stream().filter(gbp-> 
			   gbp.getFrame()==f && gbp.getAttempt()==1).mapToInt(VALIDATE_FAULT).sum();
		
	}
	
	private int sumPreviousFrame(List<GameDTO> gamesByPlayer, int frame) {
		if(frame==1) {
			return 0;
		}else {
			final int f=frame-1;
			return gamesByPlayer.stream().filter(gbp-> f == gbp.getFrame()).mapToInt(g-> Optional.ofNullable(g.getTotalFrame()).orElse(0)).sum();
		}
	}
	
	private int sumFrame(List<GameDTO> gamesByPlayer, int frame) {
		return gamesByPlayer.stream().filter(gbp-> frame == gbp.getFrame()).mapToInt(VALIDATE_FAULT).sum();
	}
	
	private boolean isStrike(List<GameDTO> gamesByPlayer, int frame) {
		return gamesByPlayer.stream().filter(gbp-> frame == gbp.getFrame()).collect(Collectors.toList()).size()==1;
	}
}
