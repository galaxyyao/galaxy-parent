package com.galaxy.authentication.domain.custom.sys;

import java.util.List;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class BindUserRoleRequest {
	private String userCode;
	private List<String> roleCodes;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<String> getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}
}
