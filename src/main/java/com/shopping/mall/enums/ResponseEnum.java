package com.shopping.mall.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SERVER_ERROR(-1, "Server Error"),

    SUCCESS(0, "Successful"),

    PASSWORD_ERROR(1, "Password is not correct"),

    USERNAME_EXIST(2, "Username is existed"),

    PARAMETER_ERROR(3, "The Value for the form is not valid"),

    EMAIL_EXIST(4, "Email is existed"),

    NEED_LOGIN(10, "Please log in first"),

    USERNAE_OR_PASSWORD_ERROR(11, "Username or Password is not correct");

    Integer code;

    String desc;

   ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
