package com.galaxy.authentication.domain.custom;

import java.io.Serializable;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;

	private final String token;

	public JwtAuthenticationResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}
