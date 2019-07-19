package com.galaxy.authentication.controller;

import java.util.List;

import com.galaxy.authentication.constant.AuthConstant;
import com.galaxy.authentication.domain.custom.sys.BindRolePrivilegeRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.galaxy.authentication.domain.repository.PrivilegeRepository;
import com.galaxy.authentication.domain.repository.RolePrivilegeRelationRepository;
import com.galaxy.authentication.domain.entity.Privilege;
import com.galaxy.authentication.service.sys.PrivilegeService;
import com.galaxy.authentication.service.sys.UserService;
import com.galaxy.common.constant.CommonConstant;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.rest.domain.JsonResult;
import com.galaxy.common.util.ReflectionUtil;
import com.galaxy.common.util.ReflectionUtil.ENTITY_SAVE_METHOD_ENUM;
import com.google.common.base.Strings;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RolePrivilegeRelationRepository rolePrivilegeRelationRepository;

    @PostMapping(value = "/privilege")
    @PreAuthorize("hasAuthority('sys_privilege')")
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> addPrivilege(@RequestBody Privilege privilege) throws BusinessException {
        if (privilege.getPrivilegeCode().contains(AuthConstant.HIERARCHY_SEPARATOR)) {
            throw new BusinessException("ATH2003").setPlaceHolder(privilege.getPrivilegeCode());
        }

        ReflectionUtil.fillCommonFields(privilege, ENTITY_SAVE_METHOD_ENUM.CREATE, userService.getCurrentUserCode());
        if (Strings.isNullOrEmpty(privilege.getParentFullCode())) {
            privilege.setPrivilegeFullCode(privilege.getPrivilegeCode());
        } else {
            Privilege parentPrivilege = privilegeRepository.findByPrivilegeFullCode(privilege.getParentFullCode())
                    .orElseThrow(() -> new BusinessException("ATH2006").setPlaceHolder(privilege.getParentFullCode()));
            if (parentPrivilege == null) {
                throw new BusinessException("ATH2004").setPlaceHolder(privilege.getParentFullCode());
            }
            privilege.setPrivilegeFullCode(privilege.getParentFullCode() + "_" + privilege.getPrivilegeCode());
        }
        privilegeRepository.save(privilege);
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
    }

    @GetMapping(value = "/userChildrenPrivilege/{privilegeFullCode}")
    public JsonResult<List<Privilege>> getUserChildrenPrivilege(@PathVariable String privilegeFullCode) throws BusinessException {
        if (AuthConstant.PRIVILEGE_ROOT_CODE.equals(privilegeFullCode)) {
            privilegeFullCode = null;
        }
        List<Privilege> userChildrenPrivilege = privilegeService.getCurrentUserChildrenPrivileges(privilegeFullCode);
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", userChildrenPrivilege);
    }

    @GetMapping(value = "/privilege/{privilegeFullCode}")
    public JsonResult<Privilege> getPrivilege(@PathVariable String privilegeFullCode) throws BusinessException {
        Privilege privilege = privilegeService.getPrivilege(privilegeFullCode);
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", privilege);
    }

    @GetMapping(value = "/privilegeTree")
    @PreAuthorize("hasAuthority('sys_privilege')")
    public JsonResult<Privilege> getPrivilegeTree() throws BusinessException {
        Privilege root = privilegeService.getPrivilegeTree();
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", root);
    }

    @GetMapping(value = "/userMenuTree/{deviceType}")
    public JsonResult<List<Privilege>> getUserMenuTree(@PathVariable String deviceType) throws BusinessException {
        List<Privilege> userMenuTree = privilegeService.getUserMenuTree(deviceType);
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", userMenuTree);
    }

    @GetMapping(value = "/userPageTree/{deviceType}")
    public JsonResult<List<Privilege>> getUserPageTree(@PathVariable String deviceType) throws BusinessException {
        List<Privilege> userPageTree = privilegeService.getUserPageTree(deviceType);
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", userPageTree);
    }

    @GetMapping(value = "/userPageAuthority/{pagePrivilegeFullCode}")
    public JsonResult<List<String>> getUserPageAuthority(@PathVariable String pagePrivilegeFullCode) throws BusinessException {
        List<String> userPageAuthorities =
                privilegeService.getCurrentUserDescendantPrivilegeFullCodes(pagePrivilegeFullCode);
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", userPageAuthorities);
    }

    @GetMapping(value = "/userPrivilege")
    public JsonResult<List<Privilege>> getUserPrivilege() throws BusinessException {
        List<Privilege> userPrivilege = privilegeService.getUserPrivileges();
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", userPrivilege);
    }

    @PostMapping(value = "/role/bind")
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> bindRolePrivilege(@RequestBody BindRolePrivilegeRequest bindRolePrivilegeRequest) throws BusinessException {
        privilegeService.bindRolePrivileges(bindRolePrivilegeRequest.getRoleCode(),
                bindRolePrivilegeRequest.getPrivilegeFullCodes());
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
    }

    @PutMapping(value = "/privilege")
    @PreAuthorize("hasAuthority('sys_privilege')")
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> editPrivilege(@RequestBody Privilege privilege) throws BusinessException {
        Privilege privilegeFromDb = privilegeRepository.findByPrivilegeFullCode(privilege.getPrivilegeFullCode())
                .orElseThrow(() -> new BusinessException("ATH2006").setPlaceHolder(privilege.getPrivilegeFullCode()));
        BeanUtils.copyProperties(privilege, privilegeFromDb, ReflectionUtil.getCommonFields("privilegeId"));
        ReflectionUtil.fillCommonFields(privilegeFromDb, ENTITY_SAVE_METHOD_ENUM.UPDATE,
                userService.getCurrentUserCode());
        privilegeRepository.save(privilegeFromDb);
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
    }

    @DeleteMapping(value = "/privilege/{privilegeFullCode}")
    @PreAuthorize("hasAuthority('sys_privilege')")
    @Transactional(rollbackFor = Exception.class)
    public JsonResult<Void> deletePrivilege(@PathVariable String privilegeFullCode) throws BusinessException {
        int rolePrivilegeBindNum = rolePrivilegeRelationRepository.countByPrivilegeFullCode(privilegeFullCode);
        if (rolePrivilegeBindNum > 0) {
            throw new BusinessException("ATH2005").setPlaceHolder(privilegeFullCode);
        }
        Privilege privilege = privilegeRepository.findByPrivilegeFullCode(privilegeFullCode)
                .orElseThrow(() -> new BusinessException("ATH2006").setPlaceHolder(privilegeFullCode));
        privilegeRepository.delete(privilege);
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
    }

    @GetMapping(value = "/rolePrivilegeLeaf/{roleCode}")
    @PreAuthorize("hasAuthority('sys_privilege')")
    public JsonResult<List<String>> getRolePrivilegeLeaf(@PathVariable String roleCode) {
        List<String> leafPrivilegeFullCodes = privilegeService.getLeafPrivilegeFullCodesByRoleCode(roleCode);
        return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", leafPrivilegeFullCodes);
    }
}
