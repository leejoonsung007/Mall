package com.shopping.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.mall.dao.ShippingMapper;
import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.form.ShippingForm;
import com.shopping.mall.pojo.Shipping;
import com.shopping.mall.service.IShippingService;
import com.shopping.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<Map<String, Integer>> addShippingDetails(Integer uid, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm, shipping);
        shipping.setUserId(uid);
        int row = shippingMapper.insertSelective(shipping);
        // cannot insert to db
        if (row == 0) {
            return ResponseVo.error(ResponseEnum.SERVER_ERROR);
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("shippingId", shipping.getId());
        return ResponseVo.success(map);
    }

    // TODO mark the status of address to be deleted rather than removing the data from DB
    @Override
    public ResponseVo deleteShippingDetails(Integer uid, Integer shippingId) {
        int row = shippingMapper.deleteByIdAndUid(uid, shippingId);
        if (row == 0) {
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_DETAIL_FAILED);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo updateShippingDetails(Integer uid, Integer shippingId, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm, shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (row == 0) {
            return ResponseVo.error(ResponseEnum.UPDATE_SHIPPING_ADDRESS_FAILED);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<PageInfo> getShippingDetails(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUid(uid);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ResponseVo.success(pageInfo);
    }
}
