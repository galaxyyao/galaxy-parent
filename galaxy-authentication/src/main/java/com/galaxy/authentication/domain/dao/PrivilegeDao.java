package com.galaxy.authentication.domain.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.galaxy.authentication.domain.entity.Privilege;
import com.galaxy.common.constant.CommonConstant;
import org.apache.ibatis.session.SqlSession;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Component
public class PrivilegeDao {
	private final SqlSession sqlSession;

	public PrivilegeDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Cacheable("userPrivilegeFullCodes")
	public List<String> selectPrivilegeFullCodesByUserCode(String userCode) {
		return this.sqlSession.selectList("selectPrivilegeFullCodesByUserCode", userCode);
	}
	
	public List<Privilege> selectUserMenuAndPagesByUserCode(String userCode, List<String> privilegeTypes) {
		Map<String, Object> paramMap = new HashMap<>(CommonConstant.HASHMAP_DEFAULT_SIZE);
		paramMap.put("userCode", userCode);
		paramMap.put("privilegeTypes", privilegeTypes);
		return this.sqlSession.selectList("selectUserMenuAndPagesByUserCode", paramMap);
	}
}
