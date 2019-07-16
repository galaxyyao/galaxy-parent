package com.galaxy.authentication.domain.custom.sys;

import java.util.List;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class BindRolePrivilegeRequest {
	private String roleCode;
	private List<String> privilegeFullCodes;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public List<String> getPrivilegeFullCodes() {
		return privilegeFullCodes;
	}

	public void setPrivilegeFullCodes(List<String> privilegeFullCodes) {
		this.privilegeFullCodes = privilegeFullCodes;
	}
}
