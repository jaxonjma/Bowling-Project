package com.jaxon.bowling.strategy;

public interface IStrategyFactory {
	IProcessorStrategy getStrategy(String filename);
}
