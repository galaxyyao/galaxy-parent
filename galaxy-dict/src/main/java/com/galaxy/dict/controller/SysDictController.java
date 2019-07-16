package com.galaxy.dict.controller;

import java.util.List;

import com.galaxy.dict.constant.SysDictConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.common.constant.CommonConstant;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.rest.domain.JsonResult;
import com.galaxy.dict.domain.entity.SysDict;
import com.galaxy.dict.service.SysDictService;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@RestController
@RequestMapping("/sysDict")
public class SysDictController {

	@Autowired
	private SysDictService sysDictService;

	@RequestMapping(value = "/sysDict", method = RequestMethod.PUT)
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> addSysDict(@RequestBody SysDict sysDict) throws BusinessException {
		if (sysDict.getSysDictCode().contains(SysDictConstant.HIERARCHY_SEPARATOR)) {
			throw new BusinessException("DIC1001").setPlaceHolder(sysDict.getSysDictCode());
		}
		// 去除误输入的空格
		sysDict.setSysDictCode(sysDict.getSysDictCode().trim());
		sysDict.setSysDictName(sysDict.getSysDictName().trim());
		sysDictService.addSysDict(sysDict);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/sysDict", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> editSysDict(@RequestBody SysDict sysDict) throws BusinessException {
		// 去除误输入的空格
		sysDict.setSysDictName(sysDict.getSysDictName().trim());
		sysDictService.editSysDict(sysDict);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/sysDict/{sysDictFullCode}", method = RequestMethod.GET)
	public JsonResult<SysDict> getSysDict(@PathVariable String sysDictFullCode) throws BusinessException {
		SysDict sysDict = sysDictService.getSysDict(sysDictFullCode);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", sysDict);
	}

	@RequestMapping(value = "/sysDict/{sysDictFullCode}", method = RequestMethod.DELETE)
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> deleteSysDict(@PathVariable String sysDictFullCode) throws BusinessException {
		sysDictService.deleteSysDict(sysDictFullCode);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/children/{parentFullCode}", method = RequestMethod.GET)
	public JsonResult<List<SysDict>> getChildren(@PathVariable String parentFullCode) {
		List<SysDict> sysDicts = sysDictService.getChildSysDicts(parentFullCode);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", sysDicts);
	}

	@RequestMapping(value = "/sysDictTree", method = RequestMethod.GET)
	public JsonResult<SysDict> getSysDictTree() {
		SysDict root = sysDictService.getSysDictTree();
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", root);
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	@CacheEvict(value = { "sysDict", "sysDictName", "childSysDicts", "bizDictCode",
			"sysDictByParentFullCodeAndBizDictCode", "sysDictByParentFullCodeAndBizDictCode",
			"sysDictNameByParentFullCodeAndBizDictCode" }, allEntries = true)
	@Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 500)
	public JsonResult<Void> refreshAllSysDicts() {
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}
}
