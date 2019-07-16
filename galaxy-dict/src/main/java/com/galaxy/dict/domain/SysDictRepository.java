package com.galaxy.dict.domain;

import java.util.List;
import java.util.Optional;

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
	/**
	 * 根据字典完整编码，找到字典
	 * @param sysDictFullCode
	 * @return
	 */
	Optional<SysDict> findBySysDictFullCode(String sysDictFullCode);

	/**
	 * 根据父字典完整编码，找到子字典列表，并按SortId排序
	 * @param parentFullCode
	 * @return
	 */
	List<SysDict> findByParentFullCodeOrderBySortId(String parentFullCode);

	/**
	 * 根据父字典完整编码 + 业务编码，找到字典
	 * @param parentFullCode
	 * @param bizDictCode
	 * @return
	 */
	Optional<SysDict> findByParentFullCodeAndBizDictCode(String parentFullCode, String bizDictCode);

	/**
	 * 根据父字典完整编码，统计子字典数量
	 * @param parentFullCode
	 * @return
	 */
	int countByParentFullCode(String parentFullCode);
}
