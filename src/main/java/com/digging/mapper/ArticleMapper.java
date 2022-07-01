package com.digging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digging.entity.Article;
import com.digging.DTO.ArticleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Mapper
@Primary
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select count(*) from article where user_id = #{userId}")
    public Integer getUserArticleNum(Long userId);

    @Select("select * from article where category_id = #{categoryId}")
    List<ArticleDTO> getArticleByCategoryId(Long categoryId);

    //文章热度在新增一条评论时+2,浏览一次时+1
    @Select("update article set article_hot = article_hot + #{num} where id = #{articleId}")
    void addArticleHot(Long articleId, Integer num);
}
