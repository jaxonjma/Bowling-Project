package com.jaxon.bowling.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaxon.bowling.model.Process;
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
	
	@Override
	public void completeProcess() {
		Process p=processRepository.findAllPendingProcesses().stream().findFirst().orElse(null);
		if(p!=null) {
			LOGGER.info(String.format("Working in process with start date %s and %s records", p.getStartDate(),p.getRecords()));
			boolean complete = gameService.showGame(p.getId());
			if(complete) {
				p.setCompletedAt(new Date());
				processRepository.save(p);
			}
		}
	}

}
