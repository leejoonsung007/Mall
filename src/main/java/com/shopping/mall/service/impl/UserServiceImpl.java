package com.shopping.mall.service.impl;

import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.enums.RoleEnum;
import com.shopping.mall.dao.UserMapper;
import com.shopping.mall.pojo.User;
import com.shopping.mall.service.IUserService;
import com.shopping.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {

        // only allow to create a user not an admin in this way
        user.setRole(RoleEnum.USER.getCode());

        //Check if username exists
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }

        //Check if email exits
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        //Encrypt password with MD5
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        //Write data to database
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            return ResponseVo.error(ResponseEnum.SERVER_ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null){
            //user is not existed
            return ResponseVo.error(ResponseEnum.USERNAE_OR_PASSWORD_ERROR);
        }
        if (!user.getPassword()
                .equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            // password is not correct
            return ResponseVo.error(ResponseEnum.USERNAE_OR_PASSWORD_ERROR);
        }
        user.setPassword(null);
        return ResponseVo.success(user);
    }
}
