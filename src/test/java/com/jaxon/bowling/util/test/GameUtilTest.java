package com.jaxon.bowling.util.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaxon.bowling.exception.BusinessException;
import com.jaxon.bowling.model.dto.GameDTO;
import com.jaxon.bowling.util.GameUtil;
import com.jaxon.bowling.util.GameUtilForTest;

@DisplayName("Unit tests on GameUtil")
public class GameUtilTest {

	@Test
	@DisplayName("Testing game list with two players: warning")
	void testTwoGamesWarning() {
		List<GameDTO> games = new ArrayList<>();
				   games.addAll(GameUtilForTest.getBaseGamesJeff());
				   games.addAll(GameUtilForTest.getBaseGamesJohn());
				   games.add(GameUtilForTest.getOverRatedGame());
		
				   Assertions.assertThrows(BusinessException.class,()-> GameUtil.validateGames(games));
		
	}
	
	@Test
	@DisplayName("Testing game list with two players: success")
	void testTwoGamesSuccess() {
		List<GameDTO> games = new ArrayList<>();
		games.addAll(GameUtilForTest.getBaseGamesJeff());
		games.addAll(GameUtilForTest.getBaseGamesJohn());
		
		GameUtil.validateGames(games);
	}
}
