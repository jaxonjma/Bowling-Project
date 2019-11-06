package com.jaxon.bowling.service.impl.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.util.StringUtils;

import com.jaxon.bowling.enums.States;
import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.model.dto.ResponseDTO;
import com.jaxon.bowling.printer.Printer;
import com.jaxon.bowling.repository.IGameRepository;
import com.jaxon.bowling.service.impl.GameServiceImpl;
import com.jaxon.bowling.util.GameUtilForTest;


@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests on GameService")
class GameServiceImplUnitTest {
	
	@Mock
	private IGameRepository gameRepository;
	
	@Mock
	private Printer printer;

	@InjectMocks
	private GameServiceImpl gameServiceImpl;
	
	private static final Long ID_PROCESO=1L;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Testing game list with two players: successful")
	void testTwoGamesSuccess() {
		List<Game> games = new ArrayList<>();
				   games.addAll(GameUtilForTest.getBaseGamesJeff());
				   games.addAll(GameUtilForTest.getBaseGamesJohn());
		Mockito.when(gameRepository.findGamesByProcess(ID_PROCESO)).thenReturn(games);
		
		ResponseDTO response = gameServiceImpl.showGame(ID_PROCESO);
		assertAll("Validating answer",
					()-> assertEquals(States.LOADED, response.getState()),
					()-> assertTrue(StringUtils.isEmpty(response.getMessage()))
				);
	}
	
	@Test
	@DisplayName("Testing game list with one player (Perfect): successful")
	void testOnePerfectGameSuccess() {
		List<Game> games = GameUtilForTest.getBaseGamesPerfectHomer();
		Mockito.when(gameRepository.findGamesByProcess(ID_PROCESO)).thenReturn(games);

		ResponseDTO response = gameServiceImpl.showGame(ID_PROCESO);
		assertAll("Validating answer",
					()-> assertEquals(States.LOADED, response.getState()),
					()-> assertTrue(StringUtils.isEmpty(response.getMessage()))
				);
	}


	@Test
	@DisplayName("Testing game list with four players (including perfect and fault game): successful")
	void testFourGamesSuccess() {
		List<Game> games = new ArrayList<>();
				   games.addAll(GameUtilForTest.getBaseGamesJeff());
				   games.addAll(GameUtilForTest.getBaseGamesJohn());
				   games.addAll(GameUtilForTest.getBaseGamesPerfectHomer());
				   games.addAll(GameUtilForTest.getBaseGamesFaulXhon());
		Mockito.when(gameRepository.findGamesByProcess(ID_PROCESO)).thenReturn(games);
		
		ResponseDTO response = gameServiceImpl.showGame(ID_PROCESO);
		assertAll("Validating answer",
					()-> assertEquals(States.LOADED, response.getState()),
					()-> assertTrue(StringUtils.isEmpty(response.getMessage()))
				);
	}

	@Test
	@DisplayName("Testing game list with two players: warning")
	void testTwoGamesWarning() {
		List<Game> games = new ArrayList<>();
				   games.addAll(GameUtilForTest.getBaseGamesJeff());
				   games.addAll(GameUtilForTest.getBaseGamesJohn());
				   games.add(GameUtilForTest.getOverRatedGame());
		Mockito.when(gameRepository.findGamesByProcess(ID_PROCESO)).thenReturn(games);
		
		ResponseDTO response = gameServiceImpl.showGame(ID_PROCESO);
		assertAll("Validating answer",
					()-> assertEquals(States.WARNING, response.getState()),
					()-> assertFalse(StringUtils.isEmpty(response.getMessage()))
				);
	}
	
	@Test
	@DisplayName("Testing order of print results: successful")
	void testOrderOfPrintResukts() {
		List<Game> games = new ArrayList<>();
		Mockito.when(gameRepository.findGamesByProcess(ID_PROCESO)).thenReturn(games);
		gameServiceImpl.printResults(ID_PROCESO);
	}
}
