package com.galaxy.authentication.service.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.galaxy.authentication.domain.repository.RoleRepository;
import com.galaxy.authentication.domain.repository.UserRoleRelationRepository;
import com.galaxy.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.galaxy.authentication.domain.entity.Role;
import com.galaxy.authentication.domain.entity.UserRoleRelation;
import com.galaxy.common.util.ReflectionUtil;
import com.galaxy.common.util.ReflectionUtil.ENTITY_SAVE_METHOD_ENUM;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRelationRepository userRoleRelationRepository;

    @Override
    public Role getRole(String roleCode) throws BusinessException {
        return roleRepository.findByRoleCode(roleCode)
                .orElseThrow(() -> new BusinessException("ATH2008").setPlaceHolder(roleCode));
    }

    @Caching(evict = {
            @CacheEvict(value = "userPrivilegeFullCodes", key = "#userCode"),
            @CacheEvict(value = "userPrivileges", key = "#userCode")
    })
    @Override
    public void bindUserRole(String userCode, List<String> roleCodes) throws BusinessException {
        List<UserRoleRelation> existingUserRoleRelations = userRoleRelationRepository.findByUserCode(userCode);
        List<String> existingUserRoleCodes = existingUserRoleRelations.stream().map(UserRoleRelation::getRoleCode)
                .collect(Collectors.toList());

        // 新增
        List<String> roleCodesToAdd = new ArrayList<>(roleCodes);
        roleCodesToAdd.removeAll(existingUserRoleCodes);
        for (String roleCodeToAdd : roleCodesToAdd) {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setUserCode(userCode);
            userRoleRelation.setRoleCode(roleCodeToAdd);
            ReflectionUtil.fillCommonFields(userRoleRelation, ENTITY_SAVE_METHOD_ENUM.CREATE, userCode);
            userRoleRelationRepository.save(userRoleRelation);
        }

        // 移除
        List<String> roleCodesToRemove = new ArrayList<>(existingUserRoleCodes);
        roleCodesToRemove.removeAll(roleCodes);
        for (String roleCodeToRemove : roleCodesToRemove) {
            UserRoleRelation userRoleRelation = userRoleRelationRepository.findByUserCodeAndRoleCode(userCode,
                    roleCodeToRemove).orElseThrow(() -> new BusinessException("ATH2008").setPlaceHolder(roleCodeToRemove));
            userRoleRelationRepository.delete(userRoleRelation);
        }
    }

    @Cacheable("userRole")
    @Override
    public List<String> getUserRoles(String userCode) {
        List<UserRoleRelation> userRoleRelations = userRoleRelationRepository.findByUserCode(userCode);
        List<String> roleCodes = userRoleRelations.stream().map(UserRoleRelation::getRoleCode)
                .collect(Collectors.toList());
        return roleCodes;
    }

    @Override
    public Boolean hasRole(String userCode, String roleCode) {
        return userRoleRelationRepository.countByUserCodeAndRoleCode(userCode, roleCode) > 0;
    }
}
