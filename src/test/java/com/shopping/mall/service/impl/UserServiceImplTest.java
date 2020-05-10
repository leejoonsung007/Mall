package com.shopping.mall.service.impl;

import com.shopping.mall.MallApplicationTests;
import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.enums.RoleEnum;
import com.shopping.mall.pojo.User;
import com.shopping.mall.service.IUserService;
import com.shopping.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImplTest extends MallApplicationTests {

    public static final String USERNAME = "Mark";
    public static final String PASSWORD = "123456";

    @Autowired
    private IUserService userService;

    @Before
    public void register() {
        User user = new User("Mark", "123456", "mark@gmail.com", RoleEnum.USER.getCode());
        userService.register(user);
    }

    @Test
    public void login() {
        ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}