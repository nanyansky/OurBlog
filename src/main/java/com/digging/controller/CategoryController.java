package com.digging.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.digging.common.Result;
import com.digging.entity.Category;
import com.digging.DTO.CategoryDTO;
import com.digging.service.ArticleService;
import com.digging.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;

    /**
     * 分类列表
     * @return 分类列表及该分类下的文章信息
     */
    @GetMapping("/list")
    public Result<List<CategoryDTO>> categoryList()
    {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(Category::getCreateTime);

        List<Category> categoryList = categoryService.list(queryWrapper);
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (Category item : categoryList)
        {
            CategoryDTO categoryDTO = new CategoryDTO();

            BeanUtils.copyProperties(item, categoryDTO);

            //查询分类对应的文章
            categoryDTO.setArticleList(articleService.getArticleByCategoryId(item.getId()));

            categoryDTOList.add(categoryDTO);
        }

        return Result.success(categoryDTOList);
    }
}
