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
	List<RolePrivilegeRelation> findByRoleCode(String roleCode);
	
	Optional<RolePrivilegeRelation> findByRoleCodeAndPrivilegeFullCode(String roleCode, String privilegeFullCode);
	
	int countByRoleCode(String roleCode);
	
	int countByPrivilegeFullCode(String privilegeFullCode);
}
