package com.shopping.mall.controller;

import com.shopping.mall.service.ICategoryService;
import com.shopping.mall.vo.CategoryVo;
import com.shopping.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/category")
    public ResponseVo<List<CategoryVo>> getAllCategories() {
        return categoryService.selectAll();
    }
}
