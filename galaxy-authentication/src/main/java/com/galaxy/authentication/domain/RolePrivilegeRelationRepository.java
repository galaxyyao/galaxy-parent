package com.galaxy.authentication.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.RolePrivilegeRelation;

@Repository
public interface RolePrivilegeRelationRepository extends JpaRepository<RolePrivilegeRelation, String> {
	List<RolePrivilegeRelation> findByRoleCode(String roleCode);
	
	Optional<RolePrivilegeRelation> findByRoleCodeAndPrivilegeFullCode(String roleCode, String privilegeFullCode);
	
	int countByRoleCode(String roleCode);
	
	int countByPrivilegeFullCode(String privilegeFullCode);
}
