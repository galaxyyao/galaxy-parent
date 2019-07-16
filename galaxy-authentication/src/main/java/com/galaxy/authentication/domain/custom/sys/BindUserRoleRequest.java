package com.galaxy.authentication.domain.custom.sys;

import java.util.List;

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
