package com.jaxon.bowling.reader.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jaxon.bowling.reader.FileReader;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests on File Reader")
public class FileReaderUnitTest {
	
private String filename;
	
	@BeforeEach
	public void init() {
		filename=new File("src/test/resources/test.txt").getAbsolutePath();
		
	}
	
	@Test
	@DisplayName("Testing reader with correct input")
	public void readTest() {
		Stream<String> lines = FileReader.read(filename).stream();
		assertEquals(lines.count(),35);
	}
	
	@Test
	@DisplayName("Testing reader with file not found")
	public void readExceptionTest() {
		filename = "test.txt";
		assertThrows(RuntimeException.class,()->{FileReader.read(filename);});
	}

}
