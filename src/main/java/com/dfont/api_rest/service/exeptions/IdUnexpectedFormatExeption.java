package com.dfont.api_rest.service.exeptions;

public class IdUnexpectedFormatExeption extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public IdUnexpectedFormatExeption(String message) {
		super(message);
	}	
}
