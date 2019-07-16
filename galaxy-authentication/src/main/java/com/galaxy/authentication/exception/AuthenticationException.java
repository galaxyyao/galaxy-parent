package com.galaxy.authentication.exception;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 8866854983029681587L;
	
	public AuthenticationException(String message) {
        super(message);
    }
	
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
