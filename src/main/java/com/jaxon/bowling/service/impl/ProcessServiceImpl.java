package com.jaxon.bowling.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaxon.bowling.enums.States;
import com.jaxon.bowling.model.Process;
import com.jaxon.bowling.model.dto.ResponseDTO;
import com.jaxon.bowling.repository.IProcessRepository;
import com.jaxon.bowling.service.GameService;
import com.jaxon.bowling.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService{
	
	@Autowired
	private IProcessRepository processRepository;
	
	@Autowired
	private GameService gameService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessServiceImpl.class);
	private Process process;
	
	@Override
	@Transactional
	public void completeProcess() {
		process=processRepository.findAllPendingProcesses().stream().findFirst().orElse(null);
		if(process!=null) {
			LOGGER.info(String.format("Working in process with start date %s and %s records", process.getStartDate(),process.getRecords()));
			ResponseDTO response = gameService.showGame(process.getId());
			process.setCompletedAt(new Date());
			if(response.getState().equals(States.LOADED)) {
				LOGGER.info(String.format("Completed at %s",processRepository.save(process).getCompletedAt().toString()));
				gameService.printResults(process.getId());
			}else if(response.getState().equals(States.WARNING)) {
				process.setError(response.getMessage());
				LOGGER.info(String.format("Completed with warning at %s",processRepository.save(process).getCompletedAt().toString()));
			}
		}
	}
	
	public Process getProcess() {
		return process;
	}

}
