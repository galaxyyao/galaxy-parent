package com.galaxy.authentication.controller;

import java.util.List;

import com.galaxy.authentication.constant.AuthConstant;
import com.galaxy.authentication.domain.custom.sys.OrgsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.galaxy.authentication.domain.custom.sys.BindUserOrgRequest;
import com.galaxy.authentication.domain.entity.Org;
import com.galaxy.authentication.service.sys.OrgService;
import com.galaxy.common.constant.CommonConstant;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.rest.domain.JsonResult;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@RestController
@RequestMapping("/org")
public class OrgController {

	@Autowired
	private OrgService orgService;

	@PostMapping(value = "/org")
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> addOrg(@RequestBody Org org) throws BusinessException {
		if (org.getOrgCode().contains(AuthConstant.HIERARCHY_SEPARATOR)) {
			throw new BusinessException("ORG1001").setPlaceHolder(org.getOrgCode());
		}
		orgService.addOrg(org);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@PutMapping(value = "/org")
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> editOrg(@RequestBody Org org) throws BusinessException {
		orgService.editOrg(org);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@GetMapping(value = "/org/{orgFullCode}")
	public JsonResult<Org> getOrg(@PathVariable String orgFullCode) throws BusinessException {
		Org org = orgService.getOrg(orgFullCode);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", org);
	}

	@DeleteMapping(value = "/org/{orgFullCode}")
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> deleteOrg(@PathVariable String orgFullCode) throws BusinessException {
		orgService.deleteOrg(orgFullCode);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}
	
	@GetMapping(value = "/children/{parentFullCode}")
	public JsonResult<List<Org>> getChildren(@PathVariable String parentFullCode) {
		List<Org> orgs = orgService.getChildOrgList(parentFullCode);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", orgs);
	}
	
	@GetMapping(value = "/orgTree")
	public JsonResult<Org> getOrgTree() {
		Org root = orgService.getOrgTree();
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS,"", root);
	}
	
	@PostMapping(value = "/user/bind")
	@Transactional(rollbackFor = Exception.class)
	public JsonResult<Void> bindUserOrg(@RequestBody BindUserOrgRequest bindUserOrgRequest) throws BusinessException {
		orgService.bindUserOrg(bindUserOrgRequest.getUserCode(), bindUserOrgRequest.getOrgCode());
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@GetMapping(value = "/list")
	public JsonResult<List<Org>> getOrgList(){
		List<Org> list = orgService.getOrgList();
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", list);
	}

	@GetMapping("/list/{orgType}")
	public JsonResult<List<Org>> getOrgList(@PathVariable("orgType") String orgType){
		List<Org> list = orgService.getOrgList(orgType);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", list);
	}

	@PostMapping("/list")
	public JsonResult<List<Org>> getOrgList(@RequestBody OrgsRequest orgsRequest){
		List<Org> list = orgService.getOrgList(orgsRequest.getOrgType(), orgsRequest.getParentFullCode());
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", list);
	}

}
