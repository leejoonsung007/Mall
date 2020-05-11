package com.shopping.mall.controller;

import com.shopping.mall.constant.MallConstant;
import com.shopping.mall.form.CartAddForm;
import com.shopping.mall.form.CartUpdateForm;
import com.shopping.mall.pojo.User;
import com.shopping.mall.service.ICartService;
import com.shopping.mall.vo.CartVo;
import com.shopping.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/cart")
    public ResponseVo<CartVo> getCartDetail(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstant.CURRENT_USER);
        return cartService.getCartDetail(user.getId());
    }

    @PostMapping("/cart")
    public ResponseVo<CartVo> addToCart(@Valid @RequestBody CartAddForm cartAddForm, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstant.CURRENT_USER);
        return cartService.addToCart(user.getId(), cartAddForm);
    }

    @PutMapping("/cart/{productId}")
    public ResponseVo<CartVo> updateCart(@Valid @RequestBody CartUpdateForm cartUpdateForm,
                                         @PathVariable Integer productId, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstant.CURRENT_USER);
        return cartService.updateCart(user.getId(), productId, cartUpdateForm);
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseVo<CartVo> removeItemFromCart(@PathVariable Integer productId, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstant.CURRENT_USER);
        return cartService.removeItemFromCart(user.getId(), productId);
    }

    @PutMapping("/cart/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstant.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/cart/unSelectAll")
    public ResponseVo<CartVo> UnselectAll(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstant.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/cart/sum")
    public ResponseVo<Integer> sum(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstant.CURRENT_USER);
        return cartService.sum(user.getId());
    }
}
