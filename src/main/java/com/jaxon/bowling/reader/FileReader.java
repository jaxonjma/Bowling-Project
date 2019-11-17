package com.jaxon.bowling.reader;


import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaxon.bowling.exception.TechnicalException;

public final class FileReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);
			
	private FileReader() {	}

	public static List<String> read(String filename) {
		return read(filename, Charset.forName("UTF-8"));
	}

	public static List<String> read(String filename, Charset charset) {
		try {
			return Files.readAllLines(Paths.get(filename), charset);
		}catch(NoSuchFileException ex) {
			throw new TechnicalException("The file associated with the argument entered was not found", ex);
		}catch (Exception ex) {
			LOGGER.error("Error processing requested file");
			throw new TechnicalException(ex.getMessage(), ex);
		}
	}


}
