package com.galaxy.authentication.domain.custom.sys;

import com.galaxy.common.rest.domain.PageObject;

public class RolesRequest {
	private String keyword;
	private PageObject pageObject;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public PageObject getPageObject() {
		return pageObject;
	}

	public void setPageObject(PageObject pageObject) {
		this.pageObject = pageObject;
	}
}
