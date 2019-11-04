package com.jaxon.bowling.service.impl.test.unit;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.printer.Printer;
import com.jaxon.bowling.repository.IGameRepository;
import com.jaxon.bowling.service.impl.GameServiceImpl;
import com.jaxon.bowling.util.test.unit.GameUtilForTest;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GameServiceImplUnitTest {

	@Mock
	private IGameRepository gameRepository;
	
	@Mock
	private Printer printer;
	
	@InjectMocks
	private GameServiceImpl service;
	
	@Before
	private void setup() {
//		MockitoAnnotations.initMocks(this);
		List<Game> games= GameUtilForTest.getBaseGamesJeff();
	   			   games.addAll(GameUtilForTest.getBaseGamesJohn());
	   	when(gameRepository.findGamesByProcess(anyLong())).thenReturn(games);
	}
	
	@Test
	public void showGameSuccess() {
		
		service.showGame(1L);
	}
	
}
