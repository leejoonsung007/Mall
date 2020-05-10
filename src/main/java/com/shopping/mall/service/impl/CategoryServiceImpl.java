package com.shopping.mall.service.impl;

import com.shopping.mall.dao.CategoryMapper;
import com.shopping.mall.pojo.Category;
import com.shopping.mall.service.ICategoryService;
import com.shopping.mall.vo.CategoryVo;
import com.shopping.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.shopping.mall.constant.MallConstant.PARENT_ID;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
//        List<CategoryVo> categoryVoList = new ArrayList<>();
        List<Category> categoryList = categoryMapper.selectAll();
//
//        for (Category category: categories) {
//            if(PARENT_ID.equals(category.getParentId())) {
//                categoryVoList.add(categoryToCategoryVo(category));
//            }
//        }

        // or use lambda + stream
        List<CategoryVo> categoryVoList = categoryList.stream()
                .filter(e -> PARENT_ID.equals(e.getParentId()))
                .map(this::categoryToCategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());

        findSubCategory(categoryList, categoryVoList);
        return ResponseVo.success(categoryVoList);
    }

    private void findSubCategory(List<Category> categoryList, List<CategoryVo> categoryVoList) {
        for (CategoryVo categoryVo: categoryVoList) {
            List<CategoryVo> subCategoryList = new ArrayList<>();

            for (Category category: categoryList) {
                if (categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subCategoryVo = categoryToCategoryVo(category);
                    subCategoryList.add(subCategoryVo);
                }
            }
            //sort the subCategories - comparator default ascending
            subCategoryList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            categoryVo.setSubCategories(subCategoryList);

            findSubCategory(categoryList, subCategoryList);
        }
    }

    private CategoryVo categoryToCategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
