package com.jaxon.bowling.exception;

public class TechnicalException extends SpiritException {
	
	private static final long serialVersionUID = -8400320598772024740L;
	
	public TechnicalException(String message) {
		super(message);
	}
	
	public TechnicalException(Throwable cause) {
		super(cause);
	}
	
	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
