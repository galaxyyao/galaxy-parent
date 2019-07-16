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
	SysDict getSysDict(String sysDictFullCode);

	String getSysDictName(String sysDictFullCode);

	List<SysDict> getChildSysDicts(String parentFullCode);

	String getBizDictCode(String sysDictFullCode);

	String getSysDictFullCodeByBizDictCode(String bizDictCode);

	SysDict getSysDictByParentFullCodeAndBizDictCode(String parentFullCode, String bizDictCode);

	String getSysDictNameByParentFullCodeAndBizDictCode(String parentFullCode, String bizDictCode);

	SysDict getSysDictTree();

	void addSysDict(SysDict sysDict) throws BusinessException;

	void editSysDict(SysDict sysDict) throws BusinessException;

	void deleteSysDict(String sysDictFullCode) throws BusinessException;

}
