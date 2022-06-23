package com.digging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digging.entity.Article;
import com.digging.entity.Tags;
import com.digging.mapper.ArticleMapper;
import com.digging.model.dto.ArticleDTO;

import javax.annotation.Resource;
import java.util.List;

public interface ArticleService extends IService<Article> {

    //获取文章标签
    public List<Tags> getTagsList(Long articleId);

    //获取用户文章数
    public Integer getUserArticleNum(Long userId);

    //获取某个分类下的文章集合
    public List<ArticleDTO> getArticleByCategoryId(Long categoryId);
}
