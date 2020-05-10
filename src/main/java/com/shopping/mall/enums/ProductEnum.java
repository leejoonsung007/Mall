package com.shopping.mall.enums;

import lombok.Getter;

@Getter
public enum ProductEnum {

    ONS_SALE(1),

    OFF_SALE(2),

    DELETED(3);

    Integer code;

    ProductEnum(Integer code) {
        this.code = code;
    }
}
