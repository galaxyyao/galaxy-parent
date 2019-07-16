package com.galaxy.authentication.domain.custom;

import java.io.Serializable;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class JwtAuthenticationRequest implements Serializable {
	private static final long serialVersionUID = -8445943548965154778L;
	
	private String username;
	private String password;
	
	public JwtAuthenticationRequest() {
        super();
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
