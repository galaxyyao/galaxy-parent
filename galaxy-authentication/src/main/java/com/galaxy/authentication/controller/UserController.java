package com.galaxy.authentication.controller;

import com.galaxy.authentication.config.AuthProperties;
import com.galaxy.authentication.domain.repository.UserRepository;
import com.galaxy.authentication.domain.custom.JwtTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.authentication.domain.custom.sys.UsersRequest;
import com.galaxy.authentication.domain.entity.User;
import com.galaxy.authentication.service.sys.RoleService;
import com.galaxy.authentication.service.sys.UserService;
import com.galaxy.common.constant.CommonConstant;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.rest.domain.JsonResult;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthProperties authProps;

	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	public JsonResult<User> getCurrentUser() throws BusinessException {
		User currentUser = userService.getCurrentUser();
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", currentUser);
	}

	@RequestMapping(value = "/userByAuthToken/get", method = RequestMethod.POST)
	public JsonResult<User> getUserByAuthToken(@RequestBody JwtTokenRequest jwtTokenRequest) throws BusinessException {
		User user = userService.getValidUserByAuthToken(jwtTokenRequest.getToken());
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", user);
	}

	@RequestMapping(value = "/users/list", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('sys_user')")
	public JsonResult<Page<User>> getUsers(@RequestBody UsersRequest usersRequest) {
		Pageable pageable = PageRequest.of(usersRequest.getPageObject().getPageNumber(),
				usersRequest.getPageObject().getPageSize(), Sort.by(new Order(Direction.ASC, "userCode")));
		Page<User> users = userRepository.findByKeyword(usersRequest.getKeyword(), pageable);
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", users);
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	@CacheEvict(value = {"user", "jwtUser", "userByUserCode", "userPrivilegeFullCodes", "userPrivileges", "userRole"},
			allEntries = true)
	@Scheduled(fixedDelay = 60 * 60 * 1000 ,  initialDelay = 500)
	public JsonResult<Void> refreshAllUsers() {
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "/userByUserCode/{userCode:.+}", method = RequestMethod.GET)
	public JsonResult<User> getUserByUserCode(@PathVariable String userCode) throws BusinessException {
		User user = userService.getValidUserByUserCode(userCode);
		if (user == null) {
			throw new BusinessException("ATH1001").setPlaceHolder(userCode);
		}
		return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", user);
	}
}
