package com.shopping.mall.exception;

import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.vo.ResponseVo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * Created by 廖师兄
 */
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
//	@ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo handle(RuntimeException e) {
        return ResponseVo.error(ResponseEnum.SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle() {
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVo notValidMethodArgumentsExceptionHadler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ResponseVo.error(ResponseEnum.PARAMETER_ERROR,
                Objects.requireNonNull(bindingResult.getFieldError()).getField() + " "
                        + bindingResult.getFieldError().getDefaultMessage());
    }

}
