package com.jaxon.bowling.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.processor.impl.GameProcessorImpl;

@Component
public class GameStrategyImpl extends ProcessorStrategyImpl{

	@Autowired
	private GameProcessorImpl gameProcessorImpl; 
	
	@Override
	protected void process(String filename) {
		gameProcessorImpl.process(Game.class, filename);
	}

}
