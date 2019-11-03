package com.jaxon.bowling.task;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaxon.bowling.service.ProcessService;

@Component
public class ShowProcessTask extends TimerTask {

	@Autowired
	private ProcessService processService;
	
	@Override
	public void run() {
		processService.completeProcess();
	}
}
