package com.galaxy.authentication.event;

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

	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {
	}
}
