package com.galaxy.authentication.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxy.common.constant.RequestConstant;
import com.galaxy.authentication.service.jwt.JwtTokenService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

	private final Logger jwtLogger = LoggerFactory.getLogger(this.getClass());

	private UserDetailsService userDetailsService;
	private JwtTokenService jwtTokenService;
	private String tokenHeader;

	public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, JwtTokenService jwtTokenService,
			String tokenHeader) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenService = jwtTokenService;
		this.tokenHeader = tokenHeader;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		jwtLogger.debug("processing authentication for '{}'", request.getRequestURL());

		final String requestHeader = request.getHeader(this.tokenHeader);

		String username = null;
		String authToken = null;
		if (!Strings.isNullOrEmpty(requestHeader) && requestHeader.startsWith(RequestConstant.AUTHORIZATION_TOKEN_PREFIX)) {
			authToken = requestHeader.substring(7);
			try {
				username = jwtTokenService.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException | MalformedJwtException | SignatureException e) {
				jwtLogger.error("an error occurred during getting username from token", e);
			} catch (ExpiredJwtException e) {
				jwtLogger.warn("the token is expired and not valid anymore", e);
			}
		} else {
			jwtLogger.warn("couldn't find bearer string, will ignore the header. request uri:" + request.getRequestURI());
		}
		
		jwtLogger.debug("checking authentication for user '{}'", username);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			jwtLogger.debug("security context was null, so authorizating user");
			try {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if (jwtTokenService.validateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					jwtLogger.info("authorizated user '{}' calling service:'{}' by method:'{}'", username,
							request.getRequestURI(), request.getMethod());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (UsernameNotFoundException e) {
				jwtLogger.error(e.getMessage(), e);
			}

		}

		chain.doFilter(request, response);
	}
}
