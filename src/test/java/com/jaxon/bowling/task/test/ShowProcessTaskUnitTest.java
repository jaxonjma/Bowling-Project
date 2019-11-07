package com.jaxon.bowling.task.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jaxon.bowling.service.ProcessService;
import com.jaxon.bowling.task.ShowProcessTask;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests on ShowProcessTask")
public class ShowProcessTaskUnitTest {

	@Mock
	private ProcessService processService;
	
	@InjectMocks
	private ShowProcessTask showProcessTask;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@DisplayName("Testing show process orchestractor task: successful")
	void testWriterListLoad() {
		showProcessTask.run();
		Mockito.verify(processService).completeProcess();
	}
}
