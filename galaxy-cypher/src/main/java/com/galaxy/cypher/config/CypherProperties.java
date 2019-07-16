package com.galaxy.cypher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Configuration
@ConfigurationProperties(prefix = "cypher")
public class CypherProperties {
	private String systemName;
	private Boolean enabled;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}