package com.galaxy.authentication.service.sys;

import java.util.List;
import java.util.stream.Collectors;

import com.galaxy.authentication.domain.repository.OrgRepository;
import com.galaxy.authentication.domain.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.authentication.domain.entity.Org;
import com.galaxy.authentication.domain.entity.User;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.util.ReflectionUtil;
import com.galaxy.common.util.ReflectionUtil.ENTITY_SAVE_METHOD_ENUM;
import com.google.common.base.Strings;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Service
public class OrgServiceImpl implements OrgService {
	@Autowired
	private OrgRepository orgRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Org getOrg(String orgFullCode) throws BusinessException {
		return orgRepository.findByOrgFullCode(orgFullCode)
				.orElseThrow(()->new BusinessException("ORG1002").setPlaceHolder(orgFullCode));
	}

	@Override
	public List<Org> getChildOrgs(String parentFullCode) {
		return orgRepository.findByParentFullCode(parentFullCode);
	}

	@Override
	public Org getOrgTree() {
		List<Org> flatList = orgRepository.findAll();
		List<Org> topLevelList = convertToTree(flatList);
		Org root = new Org();
		root.setOrgCode("root");
		root.setOrgFullCode("");
		root.setOrgName("机构根节点");
		root.setIsLeaf(topLevelList.size() > 0);
		for (Org topLevel : topLevelList) {
			topLevel.setParentFullCode("root");
		}
		root.setChildren(topLevelList);
		return root;
	}

	private List<Org> convertToTree(List<Org> flatList) {
		List<Org> treeList = flatList.stream().filter(item -> Strings.isNullOrEmpty(item.getParentFullCode()))
				.collect(Collectors.toList());
		for (Org root : treeList) {
			setChildren(root, flatList);
		}
		return treeList;
	}

	private void setChildren(Org parent, List<Org> flatList) {
		List<Org> children = flatList.stream().filter(item -> (!Strings.isNullOrEmpty(item.getParentFullCode()))
				&& item.getParentFullCode().equals(parent.getOrgFullCode())).collect(Collectors.toList());
		if (children.size() == 0) {
			parent.setIsLeaf(true);
			return;
		}
		parent.setIsLeaf(false);
		parent.setChildren(children);
		for (Org child : children) {
			setChildren(child, flatList);
		}
	}

	@Override
	public void addOrg(Org org) throws BusinessException {
		ReflectionUtil.fillCommonFields(org, ENTITY_SAVE_METHOD_ENUM.CREATE, "SYSTEM");
		if (Strings.isNullOrEmpty(org.getParentFullCode())) {
			org.setOrgFullCode(org.getOrgCode());
		} else {
			Org parentDict = orgRepository.findByOrgFullCode(org.getParentFullCode())
					.orElseThrow(()->new BusinessException("ORG1002").setPlaceHolder(org.getParentFullCode()));
			if (parentDict == null) {
				throw new BusinessException("ORG1002").setPlaceHolder(org.getOrgCode());
			}
			org.setOrgFullCode(org.getParentFullCode() + "_" + org.getOrgCode());
		}
		orgRepository.save(org);
	}

	@Override
	public void editOrg(Org org) throws BusinessException {
		Org orgFromDb = orgRepository.findByOrgFullCode(org.getOrgFullCode())
				.orElseThrow(()->new BusinessException("ORG1002").setPlaceHolder(org.getOrgFullCode()));;
		BeanUtils.copyProperties(org, orgFromDb, ReflectionUtil.getCommonFields("orgId"));
		ReflectionUtil.fillCommonFields(orgFromDb, ENTITY_SAVE_METHOD_ENUM.UPDATE, "SYSTEM");
		orgRepository.save(orgFromDb);
	}

	@Override
	public void deleteOrg(String orgFullCode) throws BusinessException {
		int childOrgNum = orgRepository.countByParentFullCode(orgFullCode);
		if (childOrgNum > 0) {
			throw new BusinessException("ORG1003").setPlaceHolder(orgFullCode);
		}
		Org org = orgRepository.findByOrgFullCode(orgFullCode)
				.orElseThrow(()->new BusinessException("ORG1002").setPlaceHolder(orgFullCode));
		if (org != null) {
			orgRepository.delete(org);
		}
	}

	@Override
	public void bindUserOrg(String userCode, String orgCode) throws BusinessException {
		User user = userService.getUnfilteredUser(userCode);
		user.setOrgFullCode(orgCode);
		userRepository.save(user);
	}

	@Override
	public Org getUserOrg(String userCode) throws BusinessException {
		User user = userService.getUnfilteredUser(userCode);
		if (user == null || Strings.isNullOrEmpty(user.getOrgFullCode())) {
			return null;
		}
		Org org = getOrg(user.getOrgFullCode());
		return org;
	}

	@Override
	public List<Org> getOrgList() {
		return orgRepository.findAll();
	}

	@Override
	public List<Org> getOrgList(String orgType) {
		return orgRepository.findByOrgType(orgType);
	}

	@Override
	public List<Org> getOrgList(String orgType, String parentFullCode) {
		return orgRepository.findByOrgTypeAndParentFullCode(orgType,parentFullCode);
	}
}
