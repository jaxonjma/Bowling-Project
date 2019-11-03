package com.jaxon.bowling.exception;

public abstract class SpiritException extends RuntimeException {
	
	private static final long serialVersionUID = -2216197270619945170L;
	
	public SpiritException(String message) {
		this(message, null);
	}
	
	public SpiritException(Throwable cause) {
		this(null, cause);
	}
	
	public SpiritException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
