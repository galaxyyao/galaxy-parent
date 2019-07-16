package com.galaxy.authentication.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.Privilege;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, String>{
	Optional<Privilege> findByPrivilegeFullCode(String privilegeFullCode);
	
	List<Privilege> findByPrivilegeFullCodeIgnoreCaseContaining(String privilegeFullCode);
	
	List<Privilege> findByParentFullCode(String parentFullCode);
	
	List<Privilege> findByPrivilegeFullCodeIn(List<String> privilegeFullCode);
}
