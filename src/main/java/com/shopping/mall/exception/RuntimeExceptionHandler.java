package com.imooc.mall.exception;

import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.exception.UserLoginException;
import com.shopping.mall.vo.UserResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 廖师兄
 */
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
//	@ResponseStatus(HttpStatus.FORBIDDEN)
    public UserResponseVo handle(RuntimeException e) {
        return UserResponseVo.error(ResponseEnum.SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public UserResponseVo userLoginHandle() {
        return UserResponseVo.error(ResponseEnum.NEED_LOGIN);
    }

}
