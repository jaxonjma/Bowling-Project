package com.jaxon.bowling.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaxon.bowling.strategy.IProcessorStrategy;
import com.jaxon.bowling.strategy.IStrategyFactory;

@Component
public class StrategyFactoryImpl implements IStrategyFactory {
	
	@Autowired
	private GameStrategyImpl strategy;

	@Override
	public IProcessorStrategy getStrategy(String filename) {
		return strategy;
	}
	
}