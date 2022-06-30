package com.digging.controller.admin;

import com.digging.common.Result;
import com.digging.entity.Article;
import com.digging.entity.User;
import com.digging.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/article")
public class ManagerArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/list")
    public Result<List<Article>> articleList(int page, int pageSize)
    {
        return Result.success(articleService.list());
    }

    @PostMapping("/update")
    public Result<String> updateUser(Article article)
    {
        articleService.updateById(article);
        return Result.success("修改成功！");
    }
}
