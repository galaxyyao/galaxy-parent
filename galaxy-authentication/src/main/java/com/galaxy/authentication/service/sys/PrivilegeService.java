package com.galaxy.authentication.service.sys;

import java.util.List;

import com.galaxy.authentication.domain.entity.Privilege;
import com.galaxy.common.exception.BusinessException;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public interface PrivilegeService {
	Privilege getPrivilege(String privilegeFullCode) throws BusinessException;

	List<Privilege> getUserMenuTree(String deviceType) throws BusinessException;

	List<Privilege> getUserPageTree(String deviceType) throws BusinessException;

	List<Privilege> getUserPrivilege() throws BusinessException;

	Privilege getPrivilegeTree();

	List<Privilege> getUserChildrenPrivilege(String privilegeFullCode) throws BusinessException;
	
	List<String> getUserDescendantPrivilegeFullCodes(String privilegeFullCode) throws BusinessException;

	void bindRolePrivileges(String roleCode, List<String> privilegeFullCodes) throws BusinessException;

	List<String> getRolePrivilegeLeaf(String roleCode);

	List<String> getUserAllPrivilegeFullCode(String userCode);
}
