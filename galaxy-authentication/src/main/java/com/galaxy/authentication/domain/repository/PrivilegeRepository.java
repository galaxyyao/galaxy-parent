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
	/**
	 * 根据权限完整编码，找到权限
	 * @param privilegeFullCode
	 * @return
	 */
	Optional<Privilege> findByPrivilegeFullCode(String privilegeFullCode);

	/**
	 * 根据忽略大小写的权限完整编码，查找所有符合的子孙权限列表
	 * @param privilegeFullCode
	 * @return
	 */
	List<Privilege> findByPrivilegeFullCodeIgnoreCaseContaining(String privilegeFullCode);

	/**
	 * 根据父权限完整编码，查找子权限列表
	 * @param parentFullCode
	 * @return
	 */
	List<Privilege> findByParentFullCode(String parentFullCode);

	/**
	 * 根据权限完整编码列表，找到对应的权限
	 * @param privilegeFullCode
	 * @return
	 */
	List<Privilege> findByPrivilegeFullCodeIn(List<String> privilegeFullCode);
}
