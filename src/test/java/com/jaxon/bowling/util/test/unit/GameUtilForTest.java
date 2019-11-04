package com.jaxon.bowling.util.test.unit;

import java.util.Arrays;
import java.util.List;

import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.util.GenericBuilder;

public final class GameUtilForTest {

	private GameUtilForTest() { }
	
	public static List<Game> getBaseGamesJeff() {
		String player="Jeff";
		Game g1  = getBaseGame(1, 1,player,10);
		Game g2  = getBaseGame(2, 1,player,7);
		Game g3  = getBaseGame(2, 1,player,3);
		Game g4  = getBaseGame(3, 1,player,9);
		Game g5  = getBaseGame(3, 2,player,0);
		Game g6  = getBaseGame(4, 1,player,10);
		Game g7  = getBaseGame(5, 1,player,0);
		Game g8  = getBaseGame(5, 2,player,8);
		Game g9  = getBaseGame(6, 1,player,8);
		Game g10 = getBaseGame(6, 2,player,2);
		Game g11 = getBaseGame(7, 1,player,-1);//It represents a fault
		Game g12 = getBaseGame(7, 2,player,6);
		Game g13 = getBaseGame(8, 1,player,10);
		Game g14 = getBaseGame(9, 1,player,10);
		Game g15 = getBaseGame(10,1,player,10);
		Game g16 = getBaseGame(10,2,player,9);
		Game g17 = getBaseGame(10,3,player,0);
			 
		return Arrays.asList(g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15,g16,g17);
	}
	
	public static List<Game> getBaseGamesJohn() {
		String player="John";
		Game g1  = getBaseGame(1, 1,player,3);
		Game g2  = getBaseGame(1, 2,player,7);
		Game g3  = getBaseGame(2, 1,player,6);
		Game g4  = getBaseGame(2, 2,player,3);
		Game g5  = getBaseGame(3, 1,player,10);
		Game g6  = getBaseGame(4, 1,player,8);
		Game g7  = getBaseGame(4, 2,player,1);
		Game g8  = getBaseGame(5, 1,player,10);
		Game g9  = getBaseGame(6, 1,player,10);
		Game g10 = getBaseGame(7, 1,player,9);
		Game g11 = getBaseGame(7, 2,player,0);
		Game g12 = getBaseGame(8, 1,player,7);
		Game g13 = getBaseGame(8, 2,player,3);
		Game g14 = getBaseGame(9, 1,player,4);
		Game g15 = getBaseGame(9, 2,player,4);
		Game g16 = getBaseGame(10,1,player,10);
		Game g17 = getBaseGame(10,2,player,9);
		Game g18 = getBaseGame(10,3,player,0);
		
		return Arrays.asList(g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15,g16,g17,g18);
	}
	
	public static List<Game> getBaseGamesPerfectHomer() {
		String player="Homer";
		Game g1  = getBaseGame(1, 1,player,10);
		Game g2  = getBaseGame(2, 1,player,10);
		Game g3  = getBaseGame(3, 1,player,10);
		Game g4  = getBaseGame(4, 1,player,10);
		Game g5  = getBaseGame(5, 1,player,10);
		Game g6  = getBaseGame(6, 1,player,10);
		Game g7  = getBaseGame(7, 1,player,10);
		Game g8  = getBaseGame(8, 1,player,10);
		Game g9  = getBaseGame(9, 1,player,10);
		Game g10 = getBaseGame(10,1,player,10);
		Game g11 = getBaseGame(10,2,player,10);
		Game g12 = getBaseGame(10,3,player,10);
		return Arrays.asList(g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12);
	}
	
	private static Game getBaseGame(int frame, int attempt, String player, int result) {
		Game g= GenericBuilder.of(Game::new).with(Game::setProcess, 1L).build();
			 g.setFrame(frame);
			 g.setAttempt(attempt);
			 g.setPlayer(player);
			 g.setResult(result);
			 
		return g;
	}
}
