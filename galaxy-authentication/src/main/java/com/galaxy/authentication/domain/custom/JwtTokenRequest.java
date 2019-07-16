package com.galaxy.authentication.domain.custom;

import java.io.Serializable;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class JwtTokenRequest implements Serializable {
	
	private static final long serialVersionUID = -4226787164463196489L;
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
