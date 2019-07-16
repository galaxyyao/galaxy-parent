package com.galaxy.authentication.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, String>{
	Optional<Privilege> findByPrivilegeFullCode(String privilegeFullCode);
	
	List<Privilege> findByPrivilegeFullCodeIgnoreCaseContaining(String privilegeFullCode);
	
	List<Privilege> findByParentFullCode(String parentFullCode);
	
	List<Privilege> findByPrivilegeFullCodeIn(List<String> privilegeFullCode);
}
