package com.jaxon.bowling.service.impl.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jaxon.bowling.enums.States;
import com.jaxon.bowling.model.Process;
import com.jaxon.bowling.model.dto.ResponseDTO;
import com.jaxon.bowling.repository.IProcessRepository;
import com.jaxon.bowling.service.GameService;
import com.jaxon.bowling.service.impl.ProcessServiceImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests on ProcessService")
public class ProcessServiceImplUnitTest {

	@Mock
	private IProcessRepository processRepository;
	
	@Mock
	private GameService gameService;
	
	@InjectMocks
	private ProcessServiceImpl processServiceImpl;
	
	private Process process;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		process= new Process();
		process.setId(1L);
	}
	
	@Test
	@DisplayName("Testing process complete: successful")
	void testProcessSuccess() {
		Mockito.when(processRepository.findAllPendingProcesses()).thenReturn(Arrays.asList(process));
		process.setCompletedAt(new Date());
		Mockito.when(processRepository.save(Mockito.any(Process.class))).thenReturn(process);
		Mockito.when(gameService.showGame(1L)).thenReturn(new ResponseDTO(States.LOADED, ""));
		Mockito.doNothing().when(gameService).printResults(1L);
		
		processServiceImpl.completeProcess();
		Process response = processServiceImpl.getProcess();
		
		assertAll("Validating process",
					()-> assertEquals(response.getState(),null),
					()-> assertFalse(response.getCompletedAt()==null)
				);
	}
	
	
	@Test
	@DisplayName("Testing process uncomplete: warning")
	void testProcessWarning() {
		Mockito.when(processRepository.findAllPendingProcesses()).thenReturn(Arrays.asList(process));
		process.setCompletedAt(new Date());
		Mockito.when(processRepository.save(Mockito.any(Process.class))).thenReturn(process);
		Mockito.when(gameService.showGame(1L)).thenReturn(new ResponseDTO(States.WARNING, "A controlled exception"));
		
		processServiceImpl.completeProcess();
		Process response = processServiceImpl.getProcess();
		
		assertAll("Validating process",
				()-> assertEquals(response.getState(),null),
				()-> assertFalse(response.getCompletedAt()==null)
				);
	}
	
}
