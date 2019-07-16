package com.galaxy.common.exception.controller;

import com.galaxy.common.bean.MessagePropertyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@RestController
public class MessageController {

	@Autowired
	private MessagePropertyHolder messageProperty;

	@RequestMapping("/message/{code}")
	public String getMessage(@PathVariable String code) {
		return messageProperty.getProperty(code);
	}
}
