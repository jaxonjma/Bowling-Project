package com.jaxon.bowling.service;

import java.util.List;

import com.jaxon.bowling.model.dto.GameDTO;

public interface GameService {
	public void printResults(List<GameDTO> games);
}
