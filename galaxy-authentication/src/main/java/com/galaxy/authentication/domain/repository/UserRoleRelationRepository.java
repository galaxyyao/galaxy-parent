package com.galaxy.authentication.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.UserRoleRelation;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Repository
public interface UserRoleRelationRepository extends JpaRepository<UserRoleRelation, String>{
	/**
	 * 根据用户编码，找到用户角色关系
	 * @param userCode
	 * @return
	 */
	List<UserRoleRelation> findByUserCode(String userCode);

	/**
	 * 根据用户编码和角色编码，找到用户角色关系
	 * @param userCode
	 * @param roleCode
	 * @return
	 */
	Optional<UserRoleRelation> findByUserCodeAndRoleCode(String userCode, String roleCode);

	/**
	 * 根据角色编码，统计符合的用户角色关系数量
	 * @param roleCode
	 * @return
	 */
	int countByRoleCode(String roleCode);

	/**
	 * 根据用户编码和角色编码，统计符合的用户角色关系数量
	 * @param userCode
	 * @param roleCode
	 * @return
	 */
	int countByUserCodeAndRoleCode(String userCode, String roleCode);
}
