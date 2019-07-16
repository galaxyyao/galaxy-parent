package com.galaxy.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Configuration
@ConfigurationProperties(prefix = "cors", ignoreUnknownFields = false)
public class CorsProperties {
	private String mapping;
	private String allowedOrigins;
	private Long maxAge;

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getAllowedOrigins() {
		return allowedOrigins;
	}

	public void setAllowedOrigins(String allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

	public Long getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Long maxAge) {
		this.maxAge = maxAge;
	}

}
