package com.jaxon.bowling.util;

import java.util.Arrays;
import java.util.List;

import com.jaxon.bowling.model.dto.GameDTO;
import com.jaxon.bowling.util.GenericBuilder;

public final class GameUtilForTest {

	private GameUtilForTest() { }
	
	public static List<GameDTO> getBaseGamesJeff() {
		String player="Jeff";
		GameDTO g1  = getBaseGame(1, 1,player,10);
		GameDTO g2  = getBaseGame(2, 1,player,7);
		GameDTO g3  = getBaseGame(2, 1,player,3);
		GameDTO g4  = getBaseGame(3, 1,player,9);
		GameDTO g5  = getBaseGame(3, 2,player,0);
		GameDTO g6  = getBaseGame(4, 1,player,10);
		GameDTO g7  = getBaseGame(5, 1,player,0);
		GameDTO g8  = getBaseGame(5, 2,player,8);
		GameDTO g9  = getBaseGame(6, 1,player,8);
		GameDTO g10 = getBaseGame(6, 2,player,2);
		GameDTO g11 = getBaseGame(7, 1,player,-1);//It represents a fault
		GameDTO g12 = getBaseGame(7, 2,player,6);
		GameDTO g13 = getBaseGame(8, 1,player,10);
		GameDTO g14 = getBaseGame(9, 1,player,10);
		GameDTO g15 = getBaseGame(10,1,player,10);
		GameDTO g16 = getBaseGame(10,2,player,9);
		GameDTO g17 = getBaseGame(10,3,player,0);
			 
		return Arrays.asList(g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15,g16,g17);
	}
	
	public static List<GameDTO> getBaseGamesJohn() {
		String player="John";
		GameDTO g1  = getBaseGame(1, 1,player,3);
		GameDTO g2  = getBaseGame(1, 2,player,7);
		GameDTO g3  = getBaseGame(2, 1,player,6);
		GameDTO g4  = getBaseGame(2, 2,player,3);
		GameDTO g5  = getBaseGame(3, 1,player,10);
		GameDTO g6  = getBaseGame(4, 1,player,8);
		GameDTO g7  = getBaseGame(4, 2,player,1);
		GameDTO g8  = getBaseGame(5, 1,player,10);
		GameDTO g9  = getBaseGame(6, 1,player,10);
		GameDTO g10 = getBaseGame(7, 1,player,9);
		GameDTO g11 = getBaseGame(7, 2,player,0);
		GameDTO g12 = getBaseGame(8, 1,player,7);
		GameDTO g13 = getBaseGame(8, 2,player,3);
		GameDTO g14 = getBaseGame(9, 1,player,4);
		GameDTO g15 = getBaseGame(9, 2,player,4);
		GameDTO g16 = getBaseGame(10,1,player,10);
		GameDTO g17 = getBaseGame(10,2,player,9);
		GameDTO g18 = getBaseGame(10,3,player,0);
		
		return Arrays.asList(g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15,g16,g17,g18);
	}
	
	public static List<GameDTO> getBaseGamesPerfectHomer() {
		String player="Homer";
		GameDTO g1  = getBaseGame(1, 1,player,10);
		GameDTO g2  = getBaseGame(2, 1,player,10);
		GameDTO g3  = getBaseGame(3, 1,player,10);
		GameDTO g4  = getBaseGame(4, 1,player,10);
		GameDTO g5  = getBaseGame(5, 1,player,10);
		GameDTO g6  = getBaseGame(6, 1,player,10);
		GameDTO g7  = getBaseGame(7, 1,player,10);
		GameDTO g8  = getBaseGame(8, 1,player,10);
		GameDTO g9  = getBaseGame(9, 1,player,10);
		GameDTO g10 = getBaseGame(10,1,player,10);
		GameDTO g11 = getBaseGame(10,2,player,10);
		GameDTO g12 = getBaseGame(10,3,player,10);
		return Arrays.asList(g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12);
	}
	
	public static List<GameDTO> getBaseGamesFaulXhon() {
		String player="Xhon";
		GameDTO g1  = getBaseGame(1, 1,player,-1);
		GameDTO g2  = getBaseGame(2, 1,player,-1);
		GameDTO g3  = getBaseGame(3, 1,player,-1);
		GameDTO g4  = getBaseGame(4, 1,player,-1);
		GameDTO g5  = getBaseGame(5, 1,player,-1);
		GameDTO g6  = getBaseGame(6, 1,player,-1);
		GameDTO g7  = getBaseGame(7, 1,player,-1);
		GameDTO g8  = getBaseGame(8, 1,player,-1);
		GameDTO g9  = getBaseGame(9, 1,player,-1);
		GameDTO g10 = getBaseGame(10,1,player,-1);
		GameDTO g11 = getBaseGame(10,2,player,-1);
		GameDTO g12 = getBaseGame(10,3,player,-1);
		return Arrays.asList(g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12);
	}
	
	public static GameDTO getOverRatedGame() {
		String player="Jeff";
		return getBaseGame(1, 1,player,11);
	}
	
	private static GameDTO getBaseGame(int frame, int attempt, String player, int result) {
		GameDTO g= GenericBuilder.of(GameDTO::new).build();
			 g.setFrame(frame);
			 g.setAttempt(attempt);
			 g.setPlayer(player);
			 g.setResult(result);
			 
		return g;
	}
}
