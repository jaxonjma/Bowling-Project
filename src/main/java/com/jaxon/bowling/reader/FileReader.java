package com.jaxon.bowling.reader;


import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaxon.bowling.exception.TechnicalException;

public final class FileReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

	private FileReader() {

	}

	public static List<String> read(String filename) {
		return read(filename, Charset.forName("UTF-8"));
	}

	public static List<String> read(String filename, Charset charset) {
		try {
			return Files.readAllLines(Paths.get(filename), charset);
		} catch (Exception ex) {
			throw new TechnicalException(ex.getMessage(), ex);
		}
	}

	public static InputStream createInputStream(String filename, Charset charset) {
		try {
			LOGGER.info("Charset {}", charset);
			return Files.newInputStream(Paths.get(filename));
		} catch (Exception ex) {
			throw new TechnicalException(ex.getMessage(), ex);
		}
	}

}
