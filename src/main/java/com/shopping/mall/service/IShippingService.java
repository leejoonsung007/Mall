package com.shopping.mall.service;

import com.github.pagehelper.PageInfo;
import com.shopping.mall.form.ShippingForm;
import com.shopping.mall.vo.ResponseVo;

import java.util.Map;

public interface IShippingService {
    ResponseVo<Map<String, Integer>> addShippingDetails(Integer uid, ShippingForm shippingForm);

    ResponseVo deleteShippingDetails(Integer uid, Integer shippingId);

    ResponseVo updateShippingDetails(Integer uid, Integer shippingId, ShippingForm shippingForm);

    ResponseVo<PageInfo> getShippingDetails(Integer uid, Integer pageNum, Integer pageSize);

}

