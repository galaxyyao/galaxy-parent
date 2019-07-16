package com.galaxy.authentication.controller;

import java.util.List;

import com.galaxy.authentication.domain.custom.sys.OrgsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.galaxy.authentication.domain.custom.sys.BindUserOrgRequest;
import com.galaxy.authentication.domain.entity.Org;
import com.galaxy.authentication.domain.entity.User;
import com.galaxy.authentication.service.sys.OrgService;
import com.galaxy.common.constant.CommonConstant;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.rest.domain.JsonResult;


@RestController
@RequestMapping("/org")
public class OrgController {

	@Autowired
	private OrgService orgService;

	@RequestMapping(value = "/org", method = RequestMethod.PUT)
	@Transactional
	public JsonResult<Void> addOrg(@RequestBody Org org) throws BusinessException {
		if (org.getOrgCode().contains("_")) {
			throw new BusinessException("ORG1001").setPlaceHolder(org.getOrgCode());
		}
		orgService.addOrg(org);
		return new JsonResult<Void>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/org", method = RequestMethod.POST)
	@Transactional
	public JsonResult<Void> editOrg(@RequestBody Org org) throws BusinessException {
		orgService.editOrg(org);
		return new JsonResult<Void>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/org/{orgFullCode}", method = RequestMethod.GET)
	public JsonResult<Org> getOrg(@PathVariable String orgFullCode) throws BusinessException {
		Org org = orgService.getOrg(orgFullCode);
		return new JsonResult<Org>(CommonConstant.JSON_RESULT_SUCCESS, "", org);
	}

	@RequestMapping(value = "/org/{orgFullCode}", method = RequestMethod.DELETE)
	@Transactional
	public JsonResult<Void> deleteOrg(@PathVariable String orgFullCode) throws BusinessException {
		orgService.deleteOrg(orgFullCode);
		return new JsonResult<Void>(CommonConstant.JSON_RESULT_SUCCESS);
	}
	
	@RequestMapping(value = "/children/{parentFullCode}", method = RequestMethod.GET)
	public JsonResult<List<Org>> getChildren(@PathVariable String parentFullCode) {
		List<Org> orgs = orgService.getChildOrgs(parentFullCode);
		return new JsonResult<List<Org>>(CommonConstant.JSON_RESULT_SUCCESS, "", orgs);
	}
	
	@RequestMapping(value = "/orgTree", method = RequestMethod.GET)
	public JsonResult<Org> getOrgTree() {
		Org root = orgService.getOrgTree();
		return new JsonResult<Org>(CommonConstant.JSON_RESULT_SUCCESS,"", root);
	}
	
	@RequestMapping(value = "/user/bind", method = RequestMethod.POST)
	@Transactional
	public JsonResult<Void> bindUserOrg(@RequestBody BindUserOrgRequest bindUserOrgRequest) throws BusinessException {
		orgService.bindUserOrg(bindUserOrgRequest.getUserCode(), bindUserOrgRequest.getOrgCode());
		return new JsonResult<Void>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JsonResult<List<Org>> getOrgList(){
		List<Org> list = orgService.getOrgList();
		return new JsonResult<List<Org>>(CommonConstant.JSON_RESULT_SUCCESS, "", list);
	}

	@GetMapping("/list/{orgType}")
	public JsonResult<List<Org>> getOrgList(@PathVariable("orgType") String orgType){
		List<Org> list = orgService.getOrgList(orgType);
		return new JsonResult<List<Org>>(CommonConstant.JSON_RESULT_SUCCESS, "", list);
	}

	@PostMapping("/list")
	public JsonResult<List<Org>> getOrgList(@RequestBody OrgsRequest orgsRequest){
		List<Org> list = orgService.getOrgList(orgsRequest.getOrgType(), orgsRequest.getParentFullCode());
		return new JsonResult<List<Org>>(CommonConstant.JSON_RESULT_SUCCESS, "", list);
	}

}