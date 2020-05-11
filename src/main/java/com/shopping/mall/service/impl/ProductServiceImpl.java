package com.shopping.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.mall.dao.ProductMapper;
import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.pojo.Product;
import com.shopping.mall.service.ICategoryService;
import com.shopping.mall.service.IProductService;
import com.shopping.mall.vo.ProductDetailVo;
import com.shopping.mall.vo.ProductVo;
import com.shopping.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.shopping.mall.enums.ProductEnum.DELETED;
import static com.shopping.mall.enums.ProductEnum.OFF_SALE;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    //categoryId can be null, the method will return all results of products
    @Override
    public ResponseVo<PageInfo> getProducts(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVo> productVoList = productList
                .stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> getProductDetail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXISTED);
        }

        if (OFF_SALE.getCode().equals(product.getStatus())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE);
        } else if (DELETED.getCode().equals(product.getStatus())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_DELETED);
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();
        // hide the sensitive data stock
        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
        BeanUtils.copyProperties(product, productDetailVo);
        return ResponseVo.success(productDetailVo);
    }
}
