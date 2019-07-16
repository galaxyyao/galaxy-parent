package com.galaxy.authentication.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
		logger.info("Application startup event triggered.");
	}
}
