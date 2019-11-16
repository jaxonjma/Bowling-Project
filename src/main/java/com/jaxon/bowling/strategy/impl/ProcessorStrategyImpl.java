package com.jaxon.bowling.strategy.impl;


import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaxon.bowling.strategy.IProcessorStrategy;


public abstract class ProcessorStrategyImpl implements IProcessorStrategy {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorStrategyImpl.class);

	private static final String TXT_REGULAR_EXPRESSION = "^.*\\.txt$";
	
	protected abstract void process(String filename);
	
	public void execute(String filename) {
		if (validate(Paths.get(filename).toFile().getName())) {
			process(filename);
		} else {
			LOGGER.warn("The uploaded file is not a .txt");
		}
	}
	
	private boolean validate(String filename) {
		return Pattern.compile(TXT_REGULAR_EXPRESSION).matcher(filename).matches();
	}
	
}

