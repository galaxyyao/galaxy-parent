package com.galaxy.authentication.controller;

import java.util.List;

import com.galaxy.authentication.domain.UserRoleRelationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.authentication.domain.RolePrivilegeRelationRepository;
import com.galaxy.authentication.domain.RoleRepository;
import com.galaxy.authentication.domain.custom.sys.BindUserRoleRequest;
import com.galaxy.authentication.domain.custom.sys.RolesRequest;
import com.galaxy.authentication.domain.entity.Role;
import com.galaxy.authentication.service.sys.RoleService;
import com.galaxy.authentication.service.sys.UserService;
import com.galaxy.common.constant.CommonConstant;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.rest.domain.JsonResult;
import com.galaxy.common.util.ReflectionUtil;
import com.galaxy.common.util.ReflectionUtil.ENTITY_SAVE_METHOD_ENUM;

@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleRelationRepository userRoleRelationRepository;

	@Autowired
	private RolePrivilegeRelationRepository rolePrivilegeRelationRepository;

	@RequestMapping(value = "/role", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('sys_role')")
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> addRole(@RequestBody Role role) throws BusinessException {
		ReflectionUtil.fillCommonFields(role, ENTITY_SAVE_METHOD_ENUM.CREATE, userService.getCurrentUserCode());
		roleRepository.save(role);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/role/{roleCode}", method = RequestMethod.GET)
	public JsonResult<Role> getRole(@PathVariable String roleCode) throws BusinessException {
		Role role = roleService.getRole(roleCode);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", role);
	}

	@RequestMapping(value = "/user/bind", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAuthority('sys_role')")
	@CacheEvict(value = {"userRole"}, key = "#bindUserRoleRequest.getUserCode()" )
	public JsonResult<Void> bindUserRole(@RequestBody BindUserRoleRequest bindUserRoleRequest) throws BusinessException {
		roleService.bindUserRole(bindUserRoleRequest.getUserCode(), bindUserRoleRequest.getRoleCodes());
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/role", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('sys_role')")
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> editRole(@RequestBody Role role) throws BusinessException {
		Role roleFromDb = roleRepository.findByRoleCode(role.getRoleCode())
				.orElseThrow(()-> new BusinessException("ATH2008").setPlaceHolder(role.getRoleCode()));
		BeanUtils.copyProperties(role, roleFromDb, ReflectionUtil.getCommonFields("roleId"));
		ReflectionUtil.fillCommonFields(roleFromDb, ENTITY_SAVE_METHOD_ENUM.UPDATE, userService.getCurrentUserCode());
		roleRepository.save(roleFromDb);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/role/{roleCode}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('sys_role')")
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> deleteRole(@PathVariable String roleCode) throws BusinessException {
		Role role = roleService.getRole(roleCode);
		int userRoleBindNum = userRoleRelationRepository.countByRoleCode(roleCode);
		if (userRoleBindNum > 0) {
			throw new BusinessException("ATH2001").setPlaceHolder(roleCode);
		}
		int rolePrivilegeBindNum = rolePrivilegeRelationRepository.countByRoleCode(roleCode);
		if (rolePrivilegeBindNum > 0) {
			throw new BusinessException("ATH2002").setPlaceHolder(roleCode);
		}
		roleRepository.delete(role);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/roles/list", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('sys_role')")
	public JsonResult<Page<Role>> getRoles(@RequestBody RolesRequest rolesRequest) {
		Pageable pageable = PageRequest.of(rolesRequest.getPageObject().getPageNumber(),
				rolesRequest.getPageObject().getPageSize(), Sort.by(new Order(Direction.ASC, "roleCode")));
		Page<Role> roles = roleRepository.findByRoleNameIgnoreCaseContainingOrRoleCodeIgnoreCaseContaining(
				rolesRequest.getKeyword(), rolesRequest.getKeyword(), pageable);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", roles);
	}

	@RequestMapping(value = "/userRole/{userCode}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('sys_role')")
	public JsonResult<List<String>> getUserRole(@PathVariable String userCode) {
		List<String> roleCodes = roleService.getUserRoles(userCode);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", roleCodes);
	}
}
