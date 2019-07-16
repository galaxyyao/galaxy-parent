package com.galaxy.authentication.service.sys;

import com.galaxy.authentication.domain.entity.User;
import com.galaxy.common.exception.BusinessException;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public interface UserService {
	/**
	 * 获取会话上下文当前用户
	 * @return
	 * @throws BusinessException
	 */
	User getCurrentUser() throws BusinessException;

	/**
	 * 获取会话上下文当前用户的编码
	 * @return
	 * @throws BusinessException
	 */
	String getCurrentUserCode() throws BusinessException;

	/**
	 * 获取未筛选的所有用户（包括锁定和已删除用户）
	 * @param userCode
	 * @return
	 * @throws BusinessException
	 */
	User getUnfilteredUser(String userCode) throws BusinessException;

	/**
	 * 根据用户编码，找到有效用户
	 * @param userCode
	 * @return
	 * @throws BusinessException
	 */
	User getValidUserByUserCode(String userCode) throws BusinessException;

	/**
	 * 根据认证token，找到有效用户
	 * @param authToken
	 * @return
	 * @throws BusinessException
	 */
	User getValidUserByAuthToken(String authToken) throws BusinessException;
}
