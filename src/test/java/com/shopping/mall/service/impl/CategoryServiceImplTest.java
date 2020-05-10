package com.shopping.mall.service.impl;

import com.shopping.mall.MallApplicationTests;
import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.service.ICategoryService;
import com.shopping.mall.vo.CategoryVo;
import com.shopping.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImplTest extends MallApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> responseVo = categoryService.selectAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}