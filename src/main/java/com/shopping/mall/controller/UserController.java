package com.shopping.mall.controller;

import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.form.UserLoginForm;
import com.shopping.mall.form.UserRegisterForm;
import com.shopping.mall.pojo.User;
import com.shopping.mall.service.IUserService;
import com.shopping.mall.vo.UserResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

import static com.shopping.mall.constant.MallConstant.CURRENT_USER;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/user/register")
    public UserResponseVo<User> register(@Valid @RequestBody UserRegisterForm userRegisterForm,
                                   BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("An error occurs, {} {}", Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return UserResponseVo.error(ResponseEnum.PARAMETER_ERROR, bindingResult);
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        //dto
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public UserResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                      BindingResult bindingResult,
                                      HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return UserResponseVo.error(ResponseEnum.PARAMETER_ERROR, bindingResult);
        }
        UserResponseVo<User> userResponseVo =  userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        // set the session
        // or get the http session from the parameters directly
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(CURRENT_USER, userResponseVo.getData());
        log.info("/login session={}", session.getId());

        return userResponseVo;
    }

    //session is saved in memory, TODO saves in token+redis - always login in
    @GetMapping("/user")
    public UserResponseVo<User> userInfo(HttpSession session) {
        log.info("/user session={}", session.getId());
        User user = (User) session.getAttribute(CURRENT_USER);
        //TODO get the latest user from Database
        //get the latest user info
        return UserResponseVo.success(user);
    }

    @PostMapping("/user/logout")
    /*
     * the setting for session timeout is located in {@link TomServletWebServerFactory} getSessionTimeoutInMinutes
     */
    public UserResponseVo<User> logout(HttpSession session) {
        log.info("/user/logout session={}", session.getId());
        session.removeAttribute(CURRENT_USER);
        return UserResponseVo.success();
    }
}
