package com.galaxy.dict.service;

import java.util.List;

import com.galaxy.common.exception.BusinessException;
import com.galaxy.dict.domain.entity.SysDict;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public interface SysDictService {
	/**
	 * 根据字典完整编码，找到字典
	 * @param sysDictFullCode
	 * @return
	 * @throws BusinessException
	 */
	SysDict getSysDict(String sysDictFullCode) throws BusinessException;

	/**
	 * 根据字典完整编码，找到字典名
	 * @param sysDictFullCode
	 * @return
	 * @throws BusinessException
	 */
	String getSysDictName(String sysDictFullCode) throws BusinessException;

	/**
	 * 根据父字典完整编码，找到子字典列表
	 * @param parentFullCode
	 * @return
	 */
	List<SysDict> getChildSysDicts(String parentFullCode);

	/**
	 * 根据字典完整编码，找到业务编码
	 * @param sysDictFullCode
	 * @return
	 * @throws BusinessException
	 */
	String getBizDictCode(String sysDictFullCode) throws BusinessException;

	/**
	 * 根据父字典完整编码和业务编码，找到字典
	 * @param parentFullCode
	 * @param bizDictCode
	 * @return
	 * @throws BusinessException
	 */
	SysDict getSysDictByParentFullCodeAndBizDictCode(String parentFullCode, String bizDictCode) throws BusinessException;

	/**
	 * 根据父字典完整编码和业务编码，找到字典名
	 * @param parentFullCode
	 * @param bizDictCode
	 * @return
	 * @throws BusinessException
	 */
	String getSysDictNameByParentFullCodeAndBizDictCode(String parentFullCode, String bizDictCode) throws BusinessException;

	/**
	 * 获取数据字典树
	 * @return
	 */
	SysDict getSysDictTree();

	/**
	 * 添加数据字典
	 * @param sysDict
	 * @throws BusinessException
	 */
	void addSysDict(SysDict sysDict) throws BusinessException;

	/**
	 * 修改数据字典信息
	 * @param sysDict
	 * @throws BusinessException
	 */
	void editSysDict(SysDict sysDict) throws BusinessException;

	/**
	 * 删除数据字典
	 * @param sysDictFullCode
	 * @throws BusinessException
	 */
	void deleteSysDict(String sysDictFullCode) throws BusinessException;

}
