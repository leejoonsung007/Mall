package com.shopping.mall.service;

import com.shopping.mall.form.CartAddForm;
import com.shopping.mall.form.CartUpdateForm;
import com.shopping.mall.vo.CartVo;
import com.shopping.mall.vo.ResponseVo;

public interface ICartService {

    ResponseVo<CartVo> addToCart(Integer uid, CartAddForm cartAddForm);

    ResponseVo<CartVo> getCartDetail(Integer uid);

    ResponseVo<CartVo> updateCart(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> removeItemFromCart(Integer uid, Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);
}
