package com.jaxon.bowling.worker.impl;


import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaxon.bowling.strategy.IProcessorStrategy;
import com.jaxon.bowling.strategy.IStrategyFactory;
import com.jaxon.bowling.worker.Worker;

@Component
public class WorkerImpl implements Worker{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkerImpl.class);
	
	@Autowired
	private IStrategyFactory factory;
	
	@Override
	public void work(Path path) {
		execute(path);
	}
	
	private void execute(Path path) {
		try {
			String name = path.toFile().getName();
			String filename = path.toFile().getAbsolutePath();
			IProcessorStrategy strategy = factory.getStrategy(name);
			if (strategy != null) {
				strategy.execute(filename);
			} else {
				LOGGER.info(String.format("No existe una estrategia para [%s]", name));
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
}
