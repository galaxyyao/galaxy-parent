package com.galaxy.authentication.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByRoleCode(String roleCode);

	Page<Role> findByRoleNameIgnoreCaseContainingOrRoleCodeIgnoreCaseContaining(String roleName, String roleCode,
			Pageable pageable);
}
