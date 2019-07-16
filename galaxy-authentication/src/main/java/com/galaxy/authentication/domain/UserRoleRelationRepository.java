package com.galaxy.authentication.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.UserRoleRelation;

@Repository
public interface UserRoleRelationRepository extends JpaRepository<UserRoleRelation, String>{
	List<UserRoleRelation> findByUserCode(String userCode);
	
	Optional<UserRoleRelation> findByUserCodeAndRoleCode(String userCode, String roleCode);
	
	int countByRoleCode(String roleCode);
	
	int countByUserCodeAndRoleCode(String userCode, String roleCode);
}
