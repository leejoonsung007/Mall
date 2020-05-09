package com.shopping.mall.service;

import com.shopping.mall.pojo.User;
import com.shopping.mall.vo.UserResponseVo;

public interface IUserService {

    UserResponseVo<User> register(User user);

    UserResponseVo<User> login (String username, String password);

}
