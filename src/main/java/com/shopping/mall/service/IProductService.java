package com.shopping.mall.service;

import com.github.pagehelper.PageInfo;
import com.shopping.mall.vo.ProductDetailVo;
import com.shopping.mall.vo.ResponseVo;

public interface IProductService {

    ResponseVo<PageInfo> getProducts(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> getProductDetail(Integer id);
}
