package com.shopping.mall.service;

import com.shopping.mall.vo.CategoryVo;
import com.shopping.mall.vo.ResponseVo;

import java.util.List;

public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();
}
