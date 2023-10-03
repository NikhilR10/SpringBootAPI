package com.mainspring.app.exception;

public class InvalidCountryException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidCountryException(String err) {
		super(err);
	}

}
