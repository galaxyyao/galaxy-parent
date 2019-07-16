package com.galaxy.authentication.domain.custom.sys;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class BindUserOrgRequest {
	private String userCode;
	private String orgCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}	
}
