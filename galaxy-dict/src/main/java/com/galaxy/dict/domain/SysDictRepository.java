package com.galaxy.dict.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxy.dict.domain.entity.SysDict;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Repository
public interface SysDictRepository extends JpaRepository<SysDict, String> {
	SysDict findOneBySysDictFullCode(String sysDictFullCode);

	SysDict findOneByBizDictCode(String bizDictCode);

	List<SysDict> findByParentFullCodeOrderBySortId(String parentFullCode);

	SysDict findOneByParentFullCodeAndBizDictCode(String parentFullCode, String bizDictCode);

	int countByParentFullCode(String parentFullCode);
	
	List<SysDict> findByParentFullCodeAndSysDictNameLikeAndIsDeleted(String parentFullCode,String name,String isDeleted);
}
