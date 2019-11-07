package com.jaxon.bowling.writer.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.util.GameUtilForTest;
import com.jaxon.bowling.writer.impl.WriterImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests on WriterImpl")
public class WriterImplUnitTest {

	@Mock
	private EntityManager manager;
	
	@InjectMocks
	private WriterImpl<Game> writerImpl;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@DisplayName("Testing writer for mass loads: successful")
	void testWriterListLoad() {
		List<Game> gamesTest = GameUtilForTest.getBaseGamesJohn();
		int counter=writerImpl.write(gamesTest.stream());
		assertEquals(gamesTest.size(), counter);
	}
	
	@Test
	@DisplayName("Testing writer for mass loads on merge task: successful")
	void testWriterListMerge() {
		List<Game> gamesTest = GameUtilForTest.getBaseGamesJohn();
		int counter=writerImpl.merge(gamesTest.stream());
		assertEquals(gamesTest.size(), counter);
	}
}
