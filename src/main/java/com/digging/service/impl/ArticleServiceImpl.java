package com.digging.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.Article;
import com.digging.entity.ArticleTags;
import com.digging.entity.Tags;
import com.digging.mapper.ArticleMapper;
import com.digging.mapper.TagsMapper;
import com.digging.model.dto.ArticleDTO;
import com.digging.service.ArticleService;
import com.digging.service.ArticleTagService;
import com.digging.service.TagsService;
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
    @Autowired
    TagsService tagsService;
    @Autowired
    ArticleTagService articleTagService;

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

    public void handleTags(Long articleId, List<String> tagsList)
    {
        for(String tag : tagsList)
        {

            if(tagsService.getOne(new LambdaQueryWrapper<Tags>().eq(Tags::getTagName, tag)) == null)
            {
                Tags tags = new Tags();
                tags.setTagName(tag);
                tagsService.save(tags);

                Long tagId = tagsService.getOne(new LambdaQueryWrapper<Tags>().eq(Tags::getTagName, tag)).getId();
                ArticleTags articleTags = new ArticleTags();
                articleTags.setArticleId(articleId);
                articleTags.setTagId(tagId);
                articleTagService.save(articleTags);
            }
            else
            {
                Long tagId = tagsService.getOne(new LambdaQueryWrapper<Tags>().eq(Tags::getTagName, tag)).getId();
                ArticleTags articleTags = new ArticleTags();
                articleTags.setArticleId(articleId);
                articleTags.setTagId(tagId);
                articleTagService.save(articleTags);
            }
        }
    }
}
