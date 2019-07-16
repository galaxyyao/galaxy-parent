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
	/**
	 * 根据角色编码，找到角色对象
	 * @param roleCode
	 * @return
	 */
	Optional<Role> findByRoleCode(String roleCode);

	/**
	 * 以忽视大小写的角色编码或角色名作为关键字，找到带分页的角色列表
	 * @param roleName
	 * @param roleCode
	 * @param pageable
	 * @return
	 */
	Page<Role> findByRoleNameIgnoreCaseContainingOrRoleCodeIgnoreCaseContaining(String roleName, String roleCode,
			Pageable pageable);
}
