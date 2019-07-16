package com.galaxy.authentication.service.sys;

import com.galaxy.authentication.domain.entity.User;
import com.galaxy.common.exception.BusinessException;

public interface UserService {
	User getCurrentUser() throws BusinessException;

	String getCurrentUserCode() throws BusinessException;

	User getUnfilteredUser(String userCode) throws BusinessException;

	User getValidUserByUserCode(String userCode) throws BusinessException;

	User getValidUserByAuthToken(String authToken) throws BusinessException;
}
