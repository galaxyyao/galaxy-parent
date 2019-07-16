package com.galaxy.authentication.domain.mapper;

import java.util.List;

import com.galaxy.authentication.domain.entity.Privilege;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrivilegeMapper {
	List<String> selectPrivilegeFullCodesByUserCode(String userCode);
	
	List<Privilege> selectUserMenuAndPagesByUserCode(String userCode, List<String> privilegeTypes);
}
