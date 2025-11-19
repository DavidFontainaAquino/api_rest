package com.dfont.api_rest.service.exeptions;

public class UserNonExistsExeption extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserNonExistsExeption(String message) {
		super(message);
	}
}
