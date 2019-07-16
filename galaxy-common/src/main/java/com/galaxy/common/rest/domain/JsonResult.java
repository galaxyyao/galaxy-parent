package com.galaxy.common.rest.domain;

import java.io.Serializable;

import com.google.common.base.Strings;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = -5711836684557771411L;

	private String message;

	private String code;

	private T object;

	public JsonResult() {
	}

	public JsonResult(String code) {
		this.code = code;
	}

	public JsonResult(String code, T object) {
		this.code = code;
		this.object = object;
	}

	public JsonResult(String code, String message) {
		this.message = message;
		this.code = code;
	}

	public JsonResult(String code, String message, T object) {
		this.message = message;
		this.code = code;
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		if (!Strings.isNullOrEmpty(code)) {
			sb.append("code:" + code);
		}
		if (!Strings.isNullOrEmpty(message)) {
			sb.append("message:" + getMessage());
		}
		if (getObject() != null) {
			sb.append("object:" + getObject());
		}
		sb.append("}");
		return sb.toString();
	}
}
