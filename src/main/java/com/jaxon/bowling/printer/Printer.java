package com.jaxon.bowling.printer;

import java.util.List;

import com.jaxon.bowling.model.Game;
import com.jaxon.bowling.model.dto.ResponseDTO;

public interface Printer {

	public ResponseDTO printGames(List<Game> games);
	
}
