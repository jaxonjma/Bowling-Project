package com.jaxon.bowling.service.impl.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jaxon.bowling.model.dto.GameDTO;
import com.jaxon.bowling.printer.Printer;
import com.jaxon.bowling.service.impl.GameServiceImpl;
import com.jaxon.bowling.util.GameUtilForTest;


@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests on GameService")
class GameServiceImplUnitTest {
	
	@Mock
	private Printer printer;

	@InjectMocks
	private GameServiceImpl gameServiceImpl;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Testing game list with two players: successful")
	void testTwoGamesSuccess() {
		List<GameDTO> games = new ArrayList<>();
				   games.addAll(GameUtilForTest.getBaseGamesJeff());
				   games.addAll(GameUtilForTest.getBaseGamesJohn());
		
		gameServiceImpl.printResults(games);
		Mockito.verify(printer).printGames(games);
	}
	
	@Test
	@DisplayName("Testing game list with one player (Perfect): successful")
	void testOnePerfectGameSuccess() {
		List<GameDTO> games = GameUtilForTest.getBaseGamesPerfectHomer();

		gameServiceImpl.printResults(games);
		Mockito.verify(printer).printGames(games);
	}

}
