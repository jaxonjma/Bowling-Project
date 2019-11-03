package com.jaxon.bowling.exception;

public class BusinessException extends SpiritException {
	
	private static final long serialVersionUID = 2943921281038327694L;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
