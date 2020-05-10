package com.shopping.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.shopping.mall.MallApplicationTests;
import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.service.IProductService;
import com.shopping.mall.vo.ProductDetailVo;
import com.shopping.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImplTest extends MallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void getProducts() {
        ResponseVo<PageInfo> responseVo = productService.getProducts(null, 1, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void getProductDetail() {
        ResponseVo<ProductDetailVo> productDetail = productService.getProductDetail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), productDetail.getStatus());
    }
}