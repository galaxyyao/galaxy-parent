package com.galaxy.authentication.service.sys;

import java.util.List;

import com.galaxy.authentication.domain.entity.Role;
import com.galaxy.common.exception.BusinessException;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public interface RoleService {
	/**
	 * 根据角色编码，找到角色
	 * @param roleCode
	 * @return
	 * @throws BusinessException
	 */
	Role getRole(String roleCode) throws BusinessException;

	/**
	 * 将用户和一系列角色编码绑定
	 * @param userCode
	 * @param roleCodes
	 * @throws BusinessException
	 */
	void bindUserRole(String userCode, List<String> roleCodes) throws BusinessException;

	/**
	 * 根据用户编码，找到角色编码列表
	 * @param userCode
	 * @return
	 */
	List<String> getUserRoles(String userCode);

	/**
	 * 判断用户是否有某个角色
	 * @param userCode
	 * @param roleCode
	 * @return
	 */
	Boolean hasRole(String userCode, String roleCode);
}
