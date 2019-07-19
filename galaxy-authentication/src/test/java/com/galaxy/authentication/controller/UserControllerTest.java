package com.galaxy.authentication.controller;

import com.galaxy.authentication.domain.entity.User;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.rest.domain.JsonResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserController userController;

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

}
