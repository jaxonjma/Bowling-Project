package com.jaxon.bowling.model.dto;

import com.jaxon.bowling.enums.States;

public class ResponseDTO {

	private String message;
	private States state;
	
	public ResponseDTO(States state, String message) {
		this.state=state;
		this.message=message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public States getState() {
		return state;
	}
	public void setState(States state) {
		this.state = state;
	}
	
	
}
