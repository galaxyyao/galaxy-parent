package com.galaxy.authentication.domain.mapper;

import java.util.List;

import com.galaxy.authentication.domain.entity.Privilege;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Mapper
public interface PrivilegeMapper {
	/**
	 * 根据用户编码，查找该用户所有的权限完整编码列表
	 * @param userCode
	 * @return
	 */
	List<String> selectPrivilegeFullCodesByUserCode(String userCode);

	/**
	 * 根据用户编码 + 权限类型，查找用户拥有的菜单和页面权限完整编码列表
	 * @param userCode
	 * @param privilegeTypes
	 * @return
	 */
	List<Privilege> selectUserMenuAndPagesByUserCode(String userCode, List<String> privilegeTypes);
}
