package com.jaxon.bowling.processor.impl.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jaxon.bowling.model.dto.GameDTO;
import com.jaxon.bowling.processor.impl.GameProcessorImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests on Game Processor")
public class GameProcessorImplTest {

	@Test
	@DisplayName("Testing read and transform file into gamesDTO: 2 players success")
	public void readAndTransformTwoPlayers() {
		GameProcessorImpl gameProcessorImpl= new GameProcessorImpl();
		List<GameDTO> games = gameProcessorImpl.read(new File("src/test/resources/test.txt").getAbsolutePath(), StandardCharsets.UTF_8);
		assertAll("Validating content",
					 ()->assertTrue(games.size()==35),
					 ()->assertTrue(games.stream().filter(g-> "Jeff".equals(g.getPlayer())).collect(Collectors.toList()).size()==17),
					 ()->assertTrue(games.stream().filter(g-> "John".equals(g.getPlayer())).collect(Collectors.toList()).size()==18)
				 );
	}
	
	@Test
	@DisplayName("Testing read and transform file into gamesDTO: 1 player success")
	public void readAndTransformOnePlayer() {
		GameProcessorImpl gameProcessorImpl= new GameProcessorImpl();
		List<GameDTO> games = gameProcessorImpl.read(new File("src/test/resources/testOnePlayer.txt").getAbsolutePath(), StandardCharsets.UTF_8);
		assertAll("Validating content",
				()->assertTrue(games.size()==17),
				()->assertTrue(games.stream().filter(g-> "Jeff".equals(g.getPlayer())).collect(Collectors.toList()).size()==17)
				);
	}
}
