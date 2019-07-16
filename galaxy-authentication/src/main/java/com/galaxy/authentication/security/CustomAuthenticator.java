package com.galaxy.authentication.security;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class CustomAuthenticator {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticator.class);

    private final String loginId;
    private final String password;

    public CustomAuthenticator(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public boolean authenticate(String loginId, String password) {
        if (Strings.isNullOrEmpty(loginId) || Strings.isNullOrEmpty(password)) {
            logger.error("loginId and password should not be empty. loginId:" + loginId);
            return false;
        }
        // TODO: add custom authentication logic
        return true;
    }

}