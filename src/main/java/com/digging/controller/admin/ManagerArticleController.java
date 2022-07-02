package com.digging.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.digging.common.Result;
import com.digging.entity.Article;
import com.digging.entity.User;
import com.digging.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return Result.success(articleService.list(new LambdaQueryWrapper<Article>().orderByDesc(Article::getCreateTime)));
    }

    @PostMapping("/update")
    public Result<String> updateUser(@RequestBody Article article)
    {
        if (articleService.updateById(article)) return Result.success("修改成功！");
        else return Result.error("未知错误！");
    }
}
