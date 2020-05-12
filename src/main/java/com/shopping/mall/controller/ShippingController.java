package com.shopping.mall.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.mall.constant.MallConstant;
import com.shopping.mall.form.ShippingForm;
import com.shopping.mall.pojo.User;
import com.shopping.mall.service.IShippingService;
import com.shopping.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@RestController
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @PostMapping("/shippings")
    public ResponseVo<Map<String, Integer>> addShippingDetails(@Valid @RequestBody ShippingForm shippingForm,
                                                               HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstant.CURRENT_USER);
        return shippingService.addShippingDetails(user.getId(), shippingForm);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo deleteShippingDetails(@PathVariable Integer shippingId, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute((MallConstant.CURRENT_USER));
        return shippingService.deleteShippingDetails(user.getId(), shippingId);
    }

    @PutMapping("shippings/{shippingId}")
    public ResponseVo updateShippingDetails(@PathVariable Integer shippingId, HttpSession httpSession,
                                            @RequestBody ShippingForm shippingForm) {
        User user = (User) httpSession.getAttribute((MallConstant.CURRENT_USER));
        return shippingService.updateShippingDetails(user.getId(), shippingId, shippingForm);
    }

    @GetMapping("/shippings")
    public ResponseVo<PageInfo> getShippingDetails(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(required = false, defaultValue = "2") Integer pageSize,
                                                   HttpSession httpSession) {
        User user = (User) httpSession.getAttribute((MallConstant.CURRENT_USER));
        return shippingService.getShippingDetails(user.getId(), pageNum, pageSize);
    }
}
