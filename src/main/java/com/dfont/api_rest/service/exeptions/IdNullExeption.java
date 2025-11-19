package com.dfont.api_rest.service.exeptions;

public class IdNullExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IdNullExeption(String message) {
		super(message);
	}
}
