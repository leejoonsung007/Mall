package com.shopping.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shopping.mall.MallApplicationTests;
import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.form.CartAddForm;
import com.shopping.mall.form.CartUpdateForm;
import com.shopping.mall.service.ICartService;
import com.shopping.mall.vo.CartVo;
import com.shopping.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CartServiceImplTest extends MallApplicationTests {

    @Autowired
    private ICartService cartService;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Integer UID = 1;
    private final Integer PRODUCT_ID = 27;

    @Before
    public void addToCart() {
        log.info("add an item to cart");
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(PRODUCT_ID);
        cartAddForm.setSelected(true);
        ResponseVo<CartVo> responseVo = cartService.addToCart(UID, cartAddForm);
        log.info("cartlist={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void getCartDetail() {
        ResponseVo<CartVo> responseVo = cartService.getCartDetail(1);
        log.info("cartlist={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void updateCartDetail() {
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(1);
        cartUpdateForm.setSelected(false);
        ResponseVo<CartVo> responseVo = cartService.updateCart(UID, PRODUCT_ID, cartUpdateForm);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @After
    public void removeItemFromCart() {
        log.info("remove an item to cart");
        ResponseVo<CartVo> responseVo = cartService.removeItemFromCart(UID, PRODUCT_ID);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void selectAll() {
        ResponseVo<CartVo> responseVo = cartService.selectAll(UID);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void unSelectAll() {
        ResponseVo<CartVo> responseVo = cartService.unSelectAll(UID);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void sum() {
        ResponseVo<Integer> responseVo = cartService.sum(UID);
        log.info("responseVo={}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}