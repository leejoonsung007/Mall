package com.shopping.mall.service;

import com.shopping.mall.pojo.User;
import com.shopping.mall.vo.ResponseVo;

public interface IUserService {

    ResponseVo<User> register(User user);

    ResponseVo<User> login (String username, String password);

}
