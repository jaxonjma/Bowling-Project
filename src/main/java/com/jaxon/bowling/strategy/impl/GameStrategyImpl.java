package com.jaxon.bowling.strategy.impl;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaxon.bowling.processor.impl.GameProcessorImpl;
import com.jaxon.bowling.service.GameService;

@Component
public class GameStrategyImpl extends ProcessorStrategyImpl{

	@Autowired
	private GameProcessorImpl gameProcessorImpl; 
	
	@Autowired
	private GameService gameService;
	
	@Override
	protected void process(String filename) {
		gameService.printResults(gameProcessorImpl.read(filename, StandardCharsets.UTF_8));
	}

}
