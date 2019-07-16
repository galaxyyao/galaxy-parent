package com.galaxy.authentication.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth", ignoreUnknownFields = false)
public class AuthProperties {
	private String mode;
	private Token token;
	private Boolean isAuthenticatePassword;
	private Boolean isEncryptPassword;
	private Jwt jwt;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Boolean getIsAuthenticatePassword() {
		return isAuthenticatePassword;
	}

	public void setIsAuthenticatePassword(Boolean isAuthenticatePassword) {
		this.isAuthenticatePassword = isAuthenticatePassword;
	}

	public Boolean getIsEncryptPassword() {
		return isEncryptPassword;
	}

	public void setIsEncryptPassword(Boolean isEncryptPassword) {
		this.isEncryptPassword = isEncryptPassword;
	}

	public Jwt getJwt() {
		return jwt;
	}

	public void setJwt(Jwt jwt) {
		this.jwt = jwt;
	}

	public static class Jwt {
		private String header;
		private String secret;
		private Long expiration;
		private String loginPath;
		private String refreshPath;
		private String ssoPath;
		private List<String> ignorePaths;

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public Long getExpiration() {
			return expiration;
		}

		public void setExpiration(Long expiration) {
			this.expiration = expiration;
		}

		public String getLoginPath() {
			return loginPath;
		}

		public void setLoginPath(String loginPath) {
			this.loginPath = loginPath;
		}

		public String getRefreshPath() {
			return refreshPath;
		}

		public void setRefreshPath(String refreshPath) {
			this.refreshPath = refreshPath;
		}

		public String getSsoPath() {
			return ssoPath;
		}

		public void setSsoPath(String ssoPath) {
			this.ssoPath = ssoPath;
		}

		public List<String> getIgnorePaths() {
			return ignorePaths;
		}

		public void setIgnorePaths(List<String> ignorePaths) {
			this.ignorePaths = ignorePaths;
		}
	}
	
	public static class Token {
		private String authCenterUrl;

		public String getAuthCenterUrl() {
			return authCenterUrl;
		}

		public void setAuthCenterUrl(String authCenterUrl) {
			this.authCenterUrl = authCenterUrl;
		}
	}
}
