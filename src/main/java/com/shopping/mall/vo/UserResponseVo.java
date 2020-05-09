package com.shopping.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shopping.mall.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserResponseVo<T> {

    private Integer status;

    private String msg;

    private T data;

    private UserResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private UserResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> UserResponseVo<T> successByMsg(String msg) {
        return new UserResponseVo<>(ResponseEnum.SUCCESS.getCode(), msg);
    }

    public static <T> UserResponseVo<T> success() {
        return new UserResponseVo<>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getDesc());
    }

    public static <T> UserResponseVo<T> success(T data) {
        return new UserResponseVo<>(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> UserResponseVo<T> error(ResponseEnum responseEnum) {
        return new UserResponseVo<>(responseEnum.getCode(), responseEnum.getDesc());
    }

    public static <T> UserResponseVo<T> error(ResponseEnum responseEnum, String msg) {
        return new UserResponseVo<>(responseEnum.getCode(), msg);
    }

    public static <T> UserResponseVo<T> error(ResponseEnum responseEnum, BindingResult bindingResult) {
        return new UserResponseVo<>(responseEnum.getCode(),
                Objects.requireNonNull(bindingResult.getFieldError()).getField() + " " +
                bindingResult.getFieldError().getDefaultMessage());
    }
}
