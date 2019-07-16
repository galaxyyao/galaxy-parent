package com.galaxy.common.exception;

import java.util.Arrays;

import com.galaxy.common.bean.MessagePropertyHolder;
import com.google.common.base.Strings;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String detailedErrorMessage;

    public BusinessException() {
    }

    public BusinessException(String errorCode) {
        this(errorCode, null);
        this.errorCode = errorCode;
    }

    public BusinessException setDetailedErrorMessage(String detailedErrorMessage) {
        if (Strings.isNullOrEmpty(detailedErrorMessage)) {
            this.detailedErrorMessage = getDetailErrorMessage(errorCode);
        } else {
            this.detailedErrorMessage = detailedErrorMessage;
        }
        return this;
    }

    public BusinessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        String detailErrorMessage = getDetailErrorMessage(errorCode);
        if (detailErrorMessage != null) {
            this.detailedErrorMessage = String.format("%s", detailErrorMessage);
        } else {
            this.detailedErrorMessage = errorCode;
        }
    }

    public BusinessException setPlaceHolder(Object... args) {
        this.detailedErrorMessage = String.format(detailedErrorMessage, args);
        return this;
    }

    public String getDetailedErrorMessage() {
        return detailedErrorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    protected void appendMessages(String... moreMessages) {
        StringBuilder sb = new StringBuilder(detailedErrorMessage);
        sb.append("More Details:");
        Arrays.asList(moreMessages).forEach(item -> {
            sb.append('|');
            sb.append(item);
        });
    }

    protected static String getDetailErrorMessage(String code) {
        // could add pattern to improve performance
        if (MessagePropertyHolder.getInstance() == null) {
            return null;
        }
        return MessagePropertyHolder.getInstance().getProperty(code);
    }
}