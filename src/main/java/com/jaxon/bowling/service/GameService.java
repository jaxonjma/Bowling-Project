package com.jaxon.bowling.service;

import com.jaxon.bowling.model.dto.ResponseDTO;

public interface GameService {

	public ResponseDTO showGame(Long idProcess);
	public void printResults(Long idProcess);
}
