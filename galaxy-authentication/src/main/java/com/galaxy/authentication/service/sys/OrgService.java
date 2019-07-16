package com.galaxy.authentication.service.sys;

import java.util.List;

import com.galaxy.authentication.domain.entity.Org;
import com.galaxy.authentication.domain.entity.User;
import com.galaxy.common.exception.BusinessException;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public interface OrgService {
	/**
	 * 根据机构完整编码，找到机构
	 * @param orgFullCode
	 * @return
	 * @throws BusinessException
	 */
	Org getOrg(String orgFullCode) throws BusinessException;

	/**
	 * 根据父机构完整编码，查找子机构列表
	 * @param parentFullCode
	 * @return
	 */
	List<Org> getChildOrgList(String parentFullCode);

	/**
	 * 获取机构树
	 * @return
	 */
	Org getOrgTree();

	/**
	 * 添加机构
	 * @param org
	 * @throws BusinessException
	 */
	void addOrg(Org org) throws BusinessException;

	/**
	 * 修改机构信息
	 * @param org
	 * @throws BusinessException
	 */
	void editOrg(Org org) throws BusinessException;

	/**
	 * 删除机构
	 * @param orgFullCode
	 * @throws BusinessException
	 */
	void deleteOrg(String orgFullCode) throws BusinessException;

	/**
	 * 将用户绑定到机构
	 * @param userCode
	 * @param orgCode
	 * @throws BusinessException
	 */
	void bindUserOrg(String userCode, String orgCode) throws BusinessException;

	/**
	 * 获取用户所属机构
	 * @param userCode
	 * @return
	 * @throws BusinessException
	 */
	Org getUserOrg(String userCode) throws BusinessException;

	/**
	 * 获取所有机构列表
	 * @return
	 */
	List<Org> getOrgList();

	/**
	 * 根据机构类型，查找符合的机构列表
	 * @param orgType
	 * @return
	 */
	List<Org> getOrgList(String orgType);

	/**
	 * 根据机构类型 + 父机构类型，查找符合的机构列表
	 * @param orgType
	 * @param parentFullCode
	 * @return
	 */
	List<Org> getOrgList(String orgType, String parentFullCode);

}
