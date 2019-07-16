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
	List<String> selectPrivilegeFullCodesByUserCode(String userCode);
	
	List<Privilege> selectUserMenuAndPagesByUserCode(String userCode, List<String> privilegeTypes);
}
