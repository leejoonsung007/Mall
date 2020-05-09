package com.shopping.mall.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN(0),

    USER(1);

    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
