package com.galaxy.common.exception;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class NoSuchBusinessCodeException extends BusinessException {

	private static final long serialVersionUID = 1110458830845926717L;

	private final static String NO_SUCH_BUSINESS_CODE = "No such Business Code:[%s]";

	private String businessCode;

	public NoSuchBusinessCodeException(String businessCode) {
		super(String.format(NO_SUCH_BUSINESS_CODE, businessCode));
		this.setBusinessCode(businessCode);
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

}
