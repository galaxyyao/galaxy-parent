package com.galaxy.authentication.domain.custom;

import java.io.Serializable;

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
