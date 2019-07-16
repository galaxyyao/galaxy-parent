package com.galaxy.authentication.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
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
