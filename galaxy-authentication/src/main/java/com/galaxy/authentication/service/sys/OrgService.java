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
	Org getOrg(String orgFullCode) throws BusinessException;

	List<Org> getChildOrgs(String parentFullCode);

	Org getOrgTree();

	void addOrg(Org org) throws BusinessException;

	void editOrg(Org org) throws BusinessException;

	void deleteOrg(String orgFullCode) throws BusinessException;

	void bindUserOrg(String userCode, String orgCode) throws BusinessException;

	Org getUserOrg(String userCode) throws BusinessException;

	List<Org> getOrgList();

	List<Org> getOrgList(String orgType);

	List<Org> getOrgList(String orgType, String parentFullCode);

}
