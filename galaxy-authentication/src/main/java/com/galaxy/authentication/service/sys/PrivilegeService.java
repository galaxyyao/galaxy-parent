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
	/**
	 * 根据权限完整编码，找到权限
	 * @param privilegeFullCode
	 * @return
	 * @throws BusinessException
	 */
	Privilege getPrivilege(String privilegeFullCode) throws BusinessException;

	/**
	 * 根据设备类型，获取用户有权限访问的菜单树
	 * @param deviceType
	 * @return
	 * @throws BusinessException
	 */
	List<Privilege> getUserMenuTree(String deviceType) throws BusinessException;

	/**
	 * 根据设备类型，获取用户有权限访问的菜单+页面树
	 * @param deviceType
	 * @return
	 * @throws BusinessException
	 */
	List<Privilege> getUserPageTree(String deviceType) throws BusinessException;

	/**
	 * 获取用户所有的权限列表
	 * @return
	 * @throws BusinessException
	 */
	List<Privilege> getUserPrivileges() throws BusinessException;

	/**
	 * 获取所有的权限树
	 * @return
	 * @throws BusinessException
	 */
	Privilege getPrivilegeTree() throws BusinessException;

	/**
	 * 根据父权限完整编码，获取当前用户有权限的子权限列表
	 * @param privilegeFullCode
	 * @return
	 * @throws BusinessException
	 */
	List<Privilege> getCurrentUserChildrenPrivileges(String privilegeFullCode) throws BusinessException;

	/**
	 * 据父权限完整编码，获取当前用户有权限的子孙权限编码列表
	 * @param privilegeFullCode
	 * @return
	 * @throws BusinessException
	 */
	List<String> getCurrentUserDescendantPrivilegeFullCodes(String privilegeFullCode) throws BusinessException;

	/**
	 * 将角色和权限绑定
	 * @param roleCode
	 * @param privilegeFullCodes
	 * @throws BusinessException
	 */
	void bindRolePrivileges(String roleCode, List<String> privilegeFullCodes) throws BusinessException;

	/**
	 * 根据角色，获取角色对应的叶子权限编码列表
	 * @param roleCode
	 * @return
	 */
	List<String> getLeafPrivilegeFullCodesByRoleCode(String roleCode);

	/**
	 * 根据用户编码，获取该用户的所有权限完整编码列表
	 * @param userCode
	 * @return
	 */
	List<String> getUserAllPrivilegeFullCode(String userCode);
}
