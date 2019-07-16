package com.galaxy.authentication.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 4999668717876145692L;

	private final String userCode;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	private final Boolean isLocked;

	public JwtUser(String userCode, String password,
			Collection<? extends GrantedAuthority> authorities, Boolean isLocked) {
		this.userCode = userCode;
		this.password = password;
		this.authorities = authorities;
		this.isLocked = isLocked;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userCode;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !isLocked;
	}

}
