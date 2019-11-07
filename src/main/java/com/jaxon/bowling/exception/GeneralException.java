package com.jaxon.bowling.exception;

public abstract class GeneralException extends RuntimeException {
	
	private static final long serialVersionUID = -2216197270619945170L;
	
	public GeneralException(String message) {
		this(message, null);
	}
	
	public GeneralException(Throwable cause) {
		this(null, cause);
	}
	
	public GeneralException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
