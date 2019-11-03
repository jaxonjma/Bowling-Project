package com.jaxon.bowling.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jaxon.bowling.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessServiceImpl.class);

	@Override
	public void completeProcess() {
		LOGGER.info("Hi again!");
		
	}

}
