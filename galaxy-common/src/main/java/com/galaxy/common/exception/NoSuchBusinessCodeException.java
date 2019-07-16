package com.galaxy.common.exception;

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
