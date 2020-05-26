package com.foo.demo.exception;

public class DemoException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	private String statusMessage;
	
	public DemoException(String message, int statusCode, String statusMessage) {
		super(message);
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}
}

