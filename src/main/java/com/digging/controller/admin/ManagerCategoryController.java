package com.digging.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.digging.common.Result;
import com.digging.entity.Article;
import com.digging.entity.Category;
import com.digging.model.dto.CategoryListDTO;
import com.digging.service.ArticleService;
import com.digging.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
public class ManagerCategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;

    //获取分类列表
    @GetMapping("/categoryList")
    public Result<List<CategoryListDTO>> getCategoryList()
    {
        List<CategoryListDTO> categoryList = new ArrayList<>();
        List<Category> list = categoryService.list();
        for(Category item : list)
        {
            CategoryListDTO categoryListDTO = new CategoryListDTO();

            categoryListDTO.setName(item.getName());

            int count = articleService.count(new LambdaQueryWrapper<Article>().eq(Article::getCategoryId, item.getId()));
            categoryListDTO.setValue(count);

            categoryList.add(categoryListDTO);
        }
        return Result.success(categoryList);
    }
}
