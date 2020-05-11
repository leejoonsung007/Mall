package com.shopping.mall.pojo;

import lombok.Data;

@Data
public class Cart {
    private Integer productId;

    private Integer quantity;

    private Boolean selected;

    public Cart(Integer productId, Integer quantity, Boolean selected) {
        this.productId = productId;
        this.quantity = quantity;
        this.selected = selected;
    }
}
