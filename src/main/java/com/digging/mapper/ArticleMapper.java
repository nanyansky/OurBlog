package com.digging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digging.entity.Article;
import com.digging.entity.Tags;
import com.digging.model.dto.ArticleDTO;
import com.digging.service.TagsService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Primary
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select count(*) from article where user_id = #{userId}")
    public Integer getUserArticleNum(Long userId);

    @Select("select * from article where category_id = #{categoryId}")
    List<ArticleDTO> getArticleByCategoryId(Long categoryId);
}
