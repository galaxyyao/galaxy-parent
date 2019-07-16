package com.galaxy.common.exception;

public class UnexpectedRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 4291081855793501806L;

	public UnexpectedRuntimeException() {
		super();
	}

	public UnexpectedRuntimeException(Exception e) {
		super(e);
	}

	public UnexpectedRuntimeException(String message) {
		super(message);
	}

	public UnexpectedRuntimeException(String message, Exception e) {
		super(message, e);
	}
}
