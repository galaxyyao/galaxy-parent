package com.galaxy.authentication.service.jwt;

import com.galaxy.authentication.security.JwtUserFactory;
import com.galaxy.authentication.service.sys.UserService;
import com.galaxy.common.constant.CommonConstant;
import com.galaxy.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.galaxy.authentication.domain.entity.User;
import com.galaxy.authentication.exception.AuthenticationException;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private JwtUserFactory jwtUserFactory;

	@Autowired
	private UserService userService;

	@Override
	@Cacheable("jwtUser")
	public UserDetails loadUserByUsername(String username) throws AuthenticationException {
		User user;
		try {
			user = userService.getValidUserByUserCode(username);
		} catch (BusinessException e) {
			throw new UsernameNotFoundException(String.format("用户不存在或已被禁用： '%s'", username));
		}
		return jwtUserFactory.create(user);
	}
}