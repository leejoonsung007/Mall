package com.shopping.mall.Inteceptor;

import com.shopping.mall.exception.UserLoginException;
import com.shopping.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.shopping.mall.constant.MallConstant.CURRENT_USER;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    // true - continue, false - stop
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        User user = (User) request.getSession().getAttribute(CURRENT_USER);
        if (user == null) {
            log.info("user=null");
            throw new UserLoginException();
        }
        return true;
    }
}
