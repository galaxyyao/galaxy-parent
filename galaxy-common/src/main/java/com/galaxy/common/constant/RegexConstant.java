package com.galaxy.common.constant;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
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
