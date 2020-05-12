package com.shopping.mall.service.impl;

import com.shopping.mall.MallApplicationTests;
import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.form.ShippingForm;
import com.shopping.mall.service.IShippingService;
import com.shopping.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class ShippingServiceImplTest extends MallApplicationTests {

    @Autowired
    private IShippingService shippingService;

    private final Integer UID = 1;

    private Integer shippingId = 8;

    private ShippingForm form;

    @Before
    public void beforeStart() {
        form = new ShippingForm();
        form.setReceiverName("Jack");
        form.setReceiverAddress("Blackrock");
        form.setReceiverCity("Dublin");
        form.setReceiverPhone("123456");
        form.setReceiverMobile("789012");
        form.setReceiverDistrict("Dublin");
        form.setReceiverProvince("Dublin");
        form.setReceiverZip("E08D");
        addShippingDetails();
    }

    public void addShippingDetails() {
        ResponseVo<Map<String, Integer>> responseVo = shippingService.addShippingDetails(UID, form);
        log.info("result={}", responseVo);
        this.shippingId = responseVo.getData().get("shippingId");
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @After
    public void deleteShippingDetails() {
        ResponseVo responseVo = shippingService.deleteShippingDetails(UID, shippingId);
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void updateShippingDetails() {
        form.setReceiverCity("Galway");
        ResponseVo responseVo = shippingService.updateShippingDetails(UID, shippingId, form);
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void getShippingDetails() {
        ResponseVo responseVo = shippingService.getShippingDetails(UID, 1, 10);
        log.info("result={}", responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}