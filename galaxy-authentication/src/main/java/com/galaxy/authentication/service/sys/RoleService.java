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
	Role getRole(String roleCode) throws BusinessException;

	void bindUserRole(String userCode, List<String> roleCodes) throws BusinessException;

	List<String> getUserRoles(String userCode);

	Boolean hasRole(String userCode, String roleCode);
}
