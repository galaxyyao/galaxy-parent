package com.galaxy.authentication.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.galaxy.authentication.exception.AuthenticationException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final Boolean isAuthenticatePassword;
	
	public CustomAuthenticationProvider(Boolean isAuthenticatePassword) {
		this.isAuthenticatePassword = isAuthenticatePassword;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginId = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		CustomAuthenticator authenticator = new CustomAuthenticator(loginId, password);
		if (!isAuthenticatePassword || authenticator.authenticate(loginId, password)) {
			Authentication auth = new UsernamePasswordAuthenticationToken(loginId, password);
			return auth;
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
