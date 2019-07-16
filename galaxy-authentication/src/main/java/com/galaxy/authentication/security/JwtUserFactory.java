package com.galaxy.authentication.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.galaxy.authentication.config.AuthProperties;
import com.galaxy.authentication.constant.AuthConstant;
import com.galaxy.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.galaxy.authentication.domain.dao.PrivilegeDao;
import com.galaxy.authentication.domain.entity.User;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Component
public class JwtUserFactory {
    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private AuthProperties authProps;

    public JwtUser create(User user) {
        Boolean isLocked =
                CommonConstant.YES.equals(user.getIsLocked()) || CommonConstant.YES.equals(user.getIsDeleted());
        // TOKEN模式是为了没有USER表的应用，能通过认证中心验证TOKEN
        if (AuthConstant.TOKEN_MODE.equals(authProps.getMode())) {
            JwtUser jwtUser = new JwtUser(user.getUserCode(), "", new ArrayList<>(), isLocked);
            return jwtUser;
        } else {
            JwtUser jwtUser = new JwtUser(user.getUserCode(), user.getPassword(),
                    mapToGrantedAuthorities(user.getUserCode()), isLocked);
            return jwtUser;
        }
    }

    @Cacheable("userPrivileges")
    public List<GrantedAuthority> mapToGrantedAuthorities(String userCode) {
        List<String> privilegeFullCodes = privilegeDao.selectPrivilegeFullCodesByUserCode(userCode);
        return privilegeFullCodes.stream().map(privilegeFullCode -> new SimpleGrantedAuthority(privilegeFullCode))
                .collect(Collectors.toList());
    }

}
