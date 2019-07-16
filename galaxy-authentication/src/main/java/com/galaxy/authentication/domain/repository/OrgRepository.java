package com.galaxy.authentication.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.Org;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Repository
public interface OrgRepository extends JpaRepository<Org, String>{
	/**
	 * 根据机构完整编码，找到机构
	 * @param orgFullCode
	 * @return
	 */
	Optional<Org> findByOrgFullCode(String orgFullCode);

	/**
	 * 根据机构父节点编码，找到子机构列表
	 * @param parentFullCode
	 * @return
	 */
	List<Org> findByParentFullCode(String parentFullCode);

	/**
	 * 根据机构父节点编码，找到子节点数量
	 * @param parentFullCode
	 * @return
	 */
	int countByParentFullCode(String parentFullCode);

	/**
	 * 根据机构编码，找到所有子孙机构
	 * @param orgFullCode
	 * @return
	 */
	List<Org> findByOrgFullCodeStartingWith(String orgFullCode);

	/**
	 * 根据机构类型搜索
	 * @param orgType
	 * @return
	 */
	List<Org> findByOrgType(String orgType);

	/**
	 * 根据机构类型和父机构编码搜索
	 * @param orgType
	 * @param parentFullCode
	 * @return
	 */
	List<Org> findByOrgTypeAndParentFullCode(String orgType, String parentFullCode);
}
