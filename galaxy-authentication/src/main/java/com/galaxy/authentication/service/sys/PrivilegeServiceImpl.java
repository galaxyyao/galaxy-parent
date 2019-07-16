package com.galaxy.authentication.service.sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.galaxy.authentication.domain.repository.PrivilegeRepository;
import com.galaxy.authentication.domain.repository.RolePrivilegeRelationRepository;
import com.galaxy.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.galaxy.authentication.domain.dao.PrivilegeDao;
import com.galaxy.authentication.domain.entity.Privilege;
import com.galaxy.authentication.domain.entity.RolePrivilegeRelation;
import com.galaxy.common.util.ReflectionUtil;
import com.galaxy.common.util.ReflectionUtil.ENTITY_SAVE_METHOD_ENUM;
import com.galaxy.dict.service.SysDictService;
import com.google.common.base.Strings;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private RolePrivilegeRelationRepository rolePrivilegeRelationRepository;

    @Override
    public Privilege getPrivilege(String privilegeFullCode) throws BusinessException {
        return privilegeRepository.findByPrivilegeFullCode(privilegeFullCode)
                .orElseThrow(() -> new BusinessException("ATH2006").setPlaceHolder(privilegeFullCode));
    }

    @Override
    public List<Privilege> getUserMenuTree(String deviceType) throws BusinessException {
        List<String> privilegeTypes = null;
        switch (deviceType) {
            case "privilege_deviceType_pc":
                privilegeTypes = Arrays.asList("privilege_type_pcMenu");
                break;
            case "privilege_deviceType_mobile":
                privilegeTypes = Arrays.asList("privilege_type_mobileMenu");
                break;
            default:
                break;
        }
        List<Privilege> flatPrivilegeList = privilegeDao
                .selectUserMenuAndPagesByUserCode(userService.getCurrentUserCode(), privilegeTypes);
        return convertToPrivilegeTree(flatPrivilegeList);
    }

    @Override
    public List<Privilege> getUserPageTree(String deviceType) throws BusinessException {
        List<String> privilegeTypes = null;
        switch (deviceType) {
            case "privilege_deviceType_pc":
                privilegeTypes = Arrays.asList("privilege_type_pcMenu", "privilege_type_pcPage");
                break;
            case "privilege_deviceType_mobile":
                privilegeTypes = Arrays.asList("privilege_type_mobileMenu", "privilege_type_mobilePage");
                break;
            default:
                break;
        }
        List<Privilege> flatPrivilegeList = privilegeDao
                .selectUserMenuAndPagesByUserCode(userService.getCurrentUserCode(), privilegeTypes);
        return convertToPrivilegeTree(flatPrivilegeList);
    }

    @Override
    public List<Privilege> getUserPrivileges() throws BusinessException {
        List<String> privilegeTypes = Arrays.asList("privilege_type_pcMenu", "privilege_type_pcPage",
                "privilege_type_mobileMenu", "privilege_type_mobilePage", "privilege_type_authority");
        List<Privilege> flatPrivilegeList = privilegeDao
                .selectUserMenuAndPagesByUserCode(userService.getCurrentUserCode(), privilegeTypes);
        for (Privilege flatPrivilege : flatPrivilegeList) {
            flatPrivilege.setPrivilegeTypeText(sysDictService.getSysDictName(flatPrivilege.getPrivilegeType()));
        }
        return flatPrivilegeList;
    }

    @Override
    public Privilege getPrivilegeTree() throws BusinessException {
        List<Privilege> flatPrivilegeList = privilegeRepository.findAll();
        List<Privilege> topLevelPrivilegeList = convertToPrivilegeTree(flatPrivilegeList);
        Privilege root = new Privilege();
        root.setPrivilegeCode("root");
        root.setPrivilegeFullCode("");
        root.setPrivilegeName("权限根节点");
        root.setIsLeaf(topLevelPrivilegeList.size() > 0);
        root.setSortId(1);
        for (Privilege topLevelPrivilege : topLevelPrivilegeList) {
            topLevelPrivilege.setParentFullCode("root");
        }
        root.setChildren(topLevelPrivilegeList);
        return root;
    }

    private List<Privilege> convertToPrivilegeTree(List<Privilege> flatPrivilegeList) throws BusinessException {
        for (Privilege flatPrivilege : flatPrivilegeList) {
            flatPrivilege.setPrivilegeTypeText(sysDictService.getSysDictName(flatPrivilege.getPrivilegeType()));
        }
        List<Privilege> treePrivilegeList = flatPrivilegeList.stream()
                .filter(privilege -> Strings.isNullOrEmpty(privilege.getParentFullCode()))
                .sorted(Comparator.comparingInt(Privilege::getSortId)).collect(Collectors.toList());
        for (Privilege rootPrivilege : treePrivilegeList) {
            setChildren(rootPrivilege, flatPrivilegeList);
        }
        return treePrivilegeList;
    }

    private void setChildren(Privilege parentPrivilege, List<Privilege> flatPrivilegeList) {
        List<Privilege> children = flatPrivilegeList.stream()
                .filter(privilege -> (!Strings.isNullOrEmpty(privilege.getParentFullCode()))
                        && privilege.getParentFullCode().equals(parentPrivilege.getPrivilegeFullCode()))
                .sorted(Comparator.comparingInt(Privilege::getSortId)).collect(Collectors.toList());
        if (children.size() == 0) {
            parentPrivilege.setIsLeaf(true);
            return;
        }
        parentPrivilege.setIsLeaf(false);
        parentPrivilege.setChildren(children);
        for (Privilege child : children) {
            setChildren(child, flatPrivilegeList);
        }
    }

    @Override
    public List<Privilege> getCurrentUserChildrenPrivileges(String privilegeFullCode) throws BusinessException {
        List<Privilege> childrenPrivilege = privilegeRepository.findByParentFullCode(privilegeFullCode);
        List<String> childrenPrivilegeFullCodes = childrenPrivilege.stream().map(Privilege::getPrivilegeFullCode)
                .collect(Collectors.toList());
        List<String> userPrivilegeFullCodes = privilegeDao
                .selectPrivilegeFullCodesByUserCode(userService.getCurrentUserCode());
        childrenPrivilegeFullCodes.retainAll(userPrivilegeFullCodes);
        List<Privilege> userChildrenPrivilege = childrenPrivilege.stream()
                .filter(p -> userPrivilegeFullCodes.contains(p.getPrivilegeFullCode())).collect(Collectors.toList());
        return userChildrenPrivilege;
    }

    @Override
    public List<String> getCurrentUserDescendantPrivilegeFullCodes(String privilegeFullCode) throws BusinessException {
        List<Privilege> descendantPrivilege = privilegeRepository
                .findByPrivilegeFullCodeIgnoreCaseContaining(privilegeFullCode);
        List<String> descendantPrivilegeFullCodes = descendantPrivilege.stream().map(Privilege::getPrivilegeFullCode)
                .collect(Collectors.toList());
        List<String> userPrivilegeFullCodes = privilegeDao
                .selectPrivilegeFullCodesByUserCode(userService.getCurrentUserCode());
        descendantPrivilegeFullCodes.retainAll(userPrivilegeFullCodes);
        return descendantPrivilegeFullCodes;
    }

    @CacheEvict(value = {"userPrivilegeFullCodes", "userPrivileges"}, allEntries = true)
    @Override
    public void bindRolePrivileges(String roleCode, List<String> privilegeFullCodes) throws BusinessException {
        List<RolePrivilegeRelation> existingRolePrivilegeRelations = rolePrivilegeRelationRepository
                .findByRoleCode(roleCode);
        List<String> existingPrivilegeFullCodes = existingRolePrivilegeRelations.stream()
                .map(RolePrivilegeRelation::getPrivilegeFullCode).collect(Collectors.toList());

        // 新增
        List<String> privilegeFullCodesToAdd = new ArrayList<>(privilegeFullCodes);
        privilegeFullCodesToAdd.removeAll(existingPrivilegeFullCodes);
        for (String privilegeFullCodeToAdd : privilegeFullCodesToAdd) {
            RolePrivilegeRelation rolePrivilegeRelation = new RolePrivilegeRelation();
            rolePrivilegeRelation.setRoleCode(roleCode);
            rolePrivilegeRelation.setPrivilegeFullCode(privilegeFullCodeToAdd);
            ReflectionUtil.fillCommonFields(rolePrivilegeRelation, ENTITY_SAVE_METHOD_ENUM.CREATE,
                    userService.getCurrentUserCode());
            rolePrivilegeRelationRepository.save(rolePrivilegeRelation);
        }

        // 移除
        List<String> privilegeFullCodesToRemove = new ArrayList<>(existingPrivilegeFullCodes);
        privilegeFullCodesToRemove.removeAll(privilegeFullCodes);
        for (String privilegeFullCodeToRemove : privilegeFullCodesToRemove) {
            RolePrivilegeRelation rolePrivilegeRelation = rolePrivilegeRelationRepository
                    .findByRoleCodeAndPrivilegeFullCode(roleCode, privilegeFullCodeToRemove)
                    .orElseThrow(() -> new BusinessException("ATH2007").setPlaceHolder(privilegeFullCodeToRemove));
            if (rolePrivilegeRelation != null) {
                rolePrivilegeRelationRepository.delete(rolePrivilegeRelation);
            }
        }
    }

    @Override
    public List<String> getLeafPrivilegeFullCodesByRoleCode(String roleCode) {
        List<RolePrivilegeRelation> rolePrivilegeRelations = rolePrivilegeRelationRepository.findByRoleCode(roleCode);
        List<String> privilegeFullCodes = rolePrivilegeRelations.stream()
                .map(RolePrivilegeRelation::getPrivilegeFullCode).collect(Collectors.toList());
        List<Privilege> privileges = privilegeRepository.findByPrivilegeFullCodeIn(privilegeFullCodes);
        List<String> leafPrivilegeFullCodes = new ArrayList<>();
        for (Privilege privilege : privileges) {
            Long childCount = privileges.stream()
                    .filter(item -> Strings.nullToEmpty(item.getParentFullCode()).equals(privilege.getPrivilegeFullCode())).count();
            if (childCount == 0) {
                leafPrivilegeFullCodes.add(privilege.getPrivilegeFullCode());
            }
        }
        return leafPrivilegeFullCodes;
    }

    @Override
    public List<String> getUserAllPrivilegeFullCode(String userCode) {
        return privilegeDao.selectPrivilegeFullCodesByUserCode(userCode);
    }
}
