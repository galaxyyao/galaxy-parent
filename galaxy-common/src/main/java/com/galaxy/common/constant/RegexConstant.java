package com.galaxy.common.constant;

public class RegexConstant {
    /**
     * 邮箱正则
     */
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

    /**
     * 手机号码正则
     */
    public static final String MOBILE_NO_PATTERN = "^1[0-9]{10}$";
}
