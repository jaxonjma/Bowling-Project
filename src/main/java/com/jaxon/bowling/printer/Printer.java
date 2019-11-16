package com.jaxon.bowling.printer;

import java.util.List;

import com.jaxon.bowling.model.dto.GameDTO;

public interface Printer {

	public void printGames(List<GameDTO> games);
	
}
