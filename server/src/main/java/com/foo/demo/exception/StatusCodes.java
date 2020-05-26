package com.foo.demo.exception;

public enum StatusCodes {

	SUCCESS("OK", 200),
	VALIDATION_ERROR("Validation error", 400),
	INTERNAL_SERVER_ERROR("Internal server error", 500),
	SERVER_TIMEOUT("Internal server error", 504);;
		
	public String message;
	public int code;

	private StatusCodes(String message, int code) {
		this.message = message;
		this.code = code;
	}
}
