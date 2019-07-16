package com.galaxy.common.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@Component
public class MessagePropertyHolder implements InitializingBean, ApplicationContextAware {
	private static final String MESSAGE_CLASSPATH = "classpath*:message.properties";

	private static ApplicationContext applicationContext;

	private Config generalConfig;

	private static <T> T getBean(Class<T> clazz) throws BeansException {
		if (applicationContext == null) {
			return (T) null;
		}
		return (T) applicationContext.getBean(clazz);
	}

	public static MessagePropertyHolder getInstance() {
		return MessagePropertyHolder.getBean(MessagePropertyHolder.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		MessagePropertyHolder.applicationContext = applicationContext;
	}

	public String getProperty(String key) {
		if (generalConfig != null) {
			return generalConfig.getString(key);
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources(MESSAGE_CLASSPATH);

		for (Resource resource : resources) {
			Config config = ConfigFactory.parseResources(resource.getFilename());
			if (generalConfig == null) {
				generalConfig = config;
			} else {
				generalConfig.withFallback(config).resolve();
			}
		}
	}

}