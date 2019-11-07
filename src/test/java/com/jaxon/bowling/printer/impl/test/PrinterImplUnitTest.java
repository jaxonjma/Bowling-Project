package com.jaxon.bowling.printer.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.printer.impl.PrinterImpl;
import com.jaxon.bowling.util.GameUtilForTest;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests on PrinterImpl")
public class PrinterImplUnitTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@InjectMocks
	private PrinterImpl printerImpl;
	
	@Before
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@DisplayName("Runing four games in printer")
	void testPrinter() throws InterruptedException {
		List<Game> games = new ArrayList<>();
		   games.addAll(GameUtilForTest.getBaseGamesJeff());
		   games.addAll(GameUtilForTest.getBaseGamesJohn());
		   games.addAll(GameUtilForTest.getBaseGamesPerfectHomer());
		   games.addAll(GameUtilForTest.getBaseGamesFaulXhon());
		   
		   
		System.setOut(new PrintStream(outContent));
		printerImpl.printGames(games);
		TimeUnit.SECONDS.sleep(5);
		assertEquals(getFullPrint().trim(), outContent.toString().trim());
	}
	
	private String getFullPrint() {
		return "\r\n" + 
				"Frame             1           2           3           4           5           6           7           8           9           10          \r\n" + 
				"Homer             \r\n" + 
				"Pinfalls                X           X           X           X           X           X           X           X           X     X     X     X     \r\n" + 
				"Score             null        null        null        null        null        null        null        null        null        null        \r\n" + 
				"Jeff              \r\n" + 
				"Pinfalls                X     7     /     9     0           X     0     8     8     /     F     6           X           X     X     9     0     \r\n" + 
				"Score             null        null        null        null        null        null        null        null        null        null        \r\n" + 
				"John              \r\n" + 
				"Pinfalls          3     /     6     3           X     8     1           X           X     9     0     7     /     4     4     X     9     0     \r\n" + 
				"Score             null        null        null        null        null        null        null        null        null        null        \r\n" + 
				"Xhon              \r\n" + 
				"Pinfalls                F           F           F           F           F           F           F           F           F     F     F     F     \r\n" + 
				"Score             null        null        null        null        null        null        null        null        null        null        ";
	}
}
