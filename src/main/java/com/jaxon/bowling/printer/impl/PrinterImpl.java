package com.jaxon.bowling.printer.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jaxon.bowling.model.dto.GameDTO;
import com.jaxon.bowling.printer.Printer;
import com.jaxon.bowling.util.GameUtil;

@Component
public class PrinterImpl implements Printer{

	private static final String STRIKE        ="X";
	private static final String FAUL          ="F";
	private static final String TEN_IN_COUPLE ="/";
	private static final String MAIN_COLUMN   ="%-18s";
	private static final String TAB_SIZE   	  ="%-6s";
	private static final String EMPTY_VALUE	  ="";

	private static final String[] FRAMES = new String[] {"1","2","3","4","5","6","7","8","9","10" };
	
	@Override
	public void printGames(List<GameDTO> games) {
		if(!games.isEmpty()) {
			System.out.println();
			this.printHeader();
		}
		Map<String,List<GameDTO>> gamesByPlayers= GameUtil.sortedMapByNamePlayer(GameUtil.getGamesByPlayers(games));
		gamesByPlayers.forEach((k,v)->{
			this.printPlayer(k);
			this.printFalls(v);
			this.printScore(v);
		});
		System.out.println();
	}
	
	private void printHeader() {
		System.out.printf(MAIN_COLUMN, "Frame");
		for (String s : FRAMES) {
			 System.out.printf(TAB_SIZE, s);
			 System.out.printf(TAB_SIZE, EMPTY_VALUE);
		}
	}
	
	private void printPlayer(String player) {
		System.out.println();
		System.out.printf(MAIN_COLUMN, player);
	}
	
	private void printFalls(List<GameDTO> gamesByPlayer) {
		System.out.println();
		System.out.printf(MAIN_COLUMN, "Pinfalls");
		Map<Integer,List<GameDTO>> gamesByFrame = GameUtil.getGamesByFrame(gamesByPlayer);
		gamesByFrame.forEach((k,v)->{
			if(v.size()==1) {
				System.out.printf(TAB_SIZE, EMPTY_VALUE);
				System.out.printf(TAB_SIZE, this.transformSingleResult(v.get(0).getResult()));
			}else if(v.size()==2) {
				System.out.printf(TAB_SIZE, this.transform(v.get(0).getResult()));
				System.out.printf(TAB_SIZE, this.transformCouple(v.get(0).getResult(),v.get(1).getResult()));
			}else if(v.size()==3) {
				System.out.printf(TAB_SIZE, this.transformSingleResult(v.get(0).getResult()));
				System.out.printf(TAB_SIZE, this.transformCouple(v.get(0).getResult(),v.get(1).getResult()));
				System.out.printf(TAB_SIZE, this.transformCouple(v.get(1).getResult(),v.get(2).getResult()));
			}
		});
	}
	
	private void printScore(List<GameDTO> gamesByPlayer) {
		System.out.println();
		System.out.printf(MAIN_COLUMN, "Score");
		Map<Integer,List<GameDTO>> gamesByFrame = GameUtil.getGamesByFrame(gamesByPlayer);
		gamesByFrame.forEach((k,v)->{
			System.out.printf(TAB_SIZE, v.get(0).getTotalFrame());
			System.out.printf(TAB_SIZE, EMPTY_VALUE);
		});
	}
	
	private String transform(Integer result) {
		if(result==-1) {
			return FAUL;
		}else {
			return String.valueOf(result);
		}
	}
	
	private String transformCouple(Integer result0, Integer result1) {
		if(result0==10) {
			return this.transformSingleResult(result1);
		}else if(result0+result1==10) {
			return TEN_IN_COUPLE;
		}else if(result1==-1) {
			return FAUL;
		}else {
			return String.valueOf(result1);
		}
	}
	
	private String transformSingleResult(Integer result) {
		if(result==10) {
			return STRIKE;
		}else if(result==-1){
			return FAUL;
		}else {
			return String.valueOf(result);
		}
	}
}
