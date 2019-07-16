package com.galaxy.authentication.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.Role;

import java.util.Optional;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByRoleCode(String roleCode);

	Page<Role> findByRoleNameIgnoreCaseContainingOrRoleCodeIgnoreCaseContaining(String roleName, String roleCode,
			Pageable pageable);
}
