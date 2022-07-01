package com.digging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digging.entity.Article;
import com.digging.entity.Tags;
import com.digging.DTO.ArticleDTO;
import com.digging.DTO.ArticleSearchDTO;

import java.util.List;

public interface ArticleService extends IService<Article> {

    //获取文章标签
    public List<Tags> getTagsList(Long articleId);

    //获取用户文章数
    public Integer getUserArticleNum(Long userId);

    //获取某个分类下的文章集合
    public List<ArticleDTO> getArticleByCategoryId(Long categoryId);

    public void handleTags(Long articleId, List<String> tagsList);

    /**
     * 搜索文章
     *
     * @param keywords 条件
     * @return 文章
     */
    List<ArticleSearchDTO> listArticlesBySearch(String keywords);

    //文章热度在新增一条评论时+2,浏览一次时+1
    void addArticleHot(Long articleId, Integer num);
}
