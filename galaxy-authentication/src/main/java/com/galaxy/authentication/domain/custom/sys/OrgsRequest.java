package com.galaxy.authentication.domain.custom.sys;

public class OrgsRequest {

    private String orgType;

    private String parentFullCode;

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getParentFullCode() {
        return parentFullCode;
    }

    public void setParentFullCode(String parentFullCode) {
        this.parentFullCode = parentFullCode;
    }
}
