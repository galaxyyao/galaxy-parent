package com.galaxy.authentication.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.RolePrivilegeRelation;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Repository
public interface RolePrivilegeRelationRepository extends JpaRepository<RolePrivilegeRelation, String> {
	/**
	 * 根据角色编码，获取角色权限对应关系列表
	 * @param roleCode
	 * @return
	 */
	List<RolePrivilegeRelation> findByRoleCode(String roleCode);

	/**
	 * 根据角色编码和权限完整编码，找到角色权限对应关系对象
	 * @param roleCode
	 * @param privilegeFullCode
	 * @return
	 */
	Optional<RolePrivilegeRelation> findByRoleCodeAndPrivilegeFullCode(String roleCode, String privilegeFullCode);

	/**
	 * 根据角色列表，统计角色权限对应关系对象数量
	 * @param roleCode
	 * @return
	 */
	int countByRoleCode(String roleCode);

	/**
	 * 根据权限完整编码列表，统计角色权限对应关系对象数量
	 * @param privilegeFullCode
	 * @return
	 */
	int countByPrivilegeFullCode(String privilegeFullCode);
}
