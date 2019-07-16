package com.galaxy.common.exception;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
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
