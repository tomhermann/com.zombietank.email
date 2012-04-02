package com.zombietank.email.exception;

public class EmailException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmailException(String message, Throwable cause) {
		super(message, cause);
	}
}
