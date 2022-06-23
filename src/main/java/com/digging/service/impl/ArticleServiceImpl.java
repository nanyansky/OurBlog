package com.digging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.Article;
import com.digging.entity.Tags;
import com.digging.mapper.ArticleMapper;
import com.digging.mapper.TagsMapper;
import com.digging.model.dto.ArticleDTO;
import com.digging.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    ArticleMapper articleMapper;
    @Resource
    TagsMapper tagsMapper;

    @Override
    public List<Tags> getTagsList(Long articleId) {
        return tagsMapper.getTagsList(articleId);
    }

    @Override
    public Integer getUserArticleNum(Long userId) {
        return articleMapper.getUserArticleNum(userId);
    }

    @Override
    public List<ArticleDTO> getArticleByCategoryId(Long categoryId) {
        return articleMapper.getArticleByCategoryId(categoryId);
    }

}
