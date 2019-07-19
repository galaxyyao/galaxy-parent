package com.galaxy.authentication.controller;

import com.galaxy.authentication.BaseTest;
import com.galaxy.authentication.domain.entity.User;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.rest.domain.JsonResult;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Objects;

public class UserControllerTest extends BaseTest {

    @Autowired
    private UserController userController;

    @Ignore
    @Test
    @WithMockUser(username = "richard.qian")
    public void getUsersTest() {
        try {
            JsonResult<User> jsonResult = userController.getCurrentUser();
            Assert.assertTrue(Objects.nonNull(jsonResult.getObject()));
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sqlTest(){
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        List<User> userList = jdbcTemplate.query("select * from tuser", rowMapper);
        Assert.assertFalse(userList.isEmpty());
    }

}
