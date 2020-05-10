package com.shopping.mall.controller;

import com.github.pagehelper.PageInfo;
import com.shopping.mall.service.IProductService;
import com.shopping.mall.vo.ProductDetailVo;
import com.shopping.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseVo<PageInfo> getProducts(@RequestParam(required = false) Integer categoryId,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        return productService.getProducts(categoryId, pageNum, pageSize);
    }

    @GetMapping("/products/{productId}")
    public ResponseVo<ProductDetailVo> getProductDetail(@PathVariable Integer productId) {
        return productService.getProductDetail(productId);
    }
}
