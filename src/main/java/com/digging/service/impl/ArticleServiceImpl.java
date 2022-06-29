package com.digging.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.Article;
import com.digging.entity.ArticleTags;
import com.digging.entity.Tags;
import com.digging.mapper.ArticleMapper;
import com.digging.mapper.TagsMapper;
import com.digging.model.dto.ArticleDTO;
import com.digging.model.dto.ArticleSearchDTO;
import com.digging.service.ArticleService;
import com.digging.service.ArticleTagService;
import com.digging.service.TagsService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

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

    @Override
    public List<ArticleSearchDTO> listArticlesBySearch(String keywords) {
        return searchArticle(buildQuery(keywords));
    }


    /**
     * 搜索文章构造
     *
     * @param keywords 条件
     * @return es条件构造器
     */
    private NativeSearchQueryBuilder buildQuery(String keywords) {
        // 条件构造器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 根据关键词搜索文章标题或内容
        if (Objects.nonNull(keywords)) {
            boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name", keywords))
                            .should(QueryBuilders.matchQuery("content", keywords)))
                    .must(QueryBuilders.termQuery("available", 0));
        }
        // 查询
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        return nativeSearchQueryBuilder;
    }


    /**
     * 文章搜索结果高亮
     *
     * @param nativeSearchQueryBuilder es条件构造器
     * @return 搜索结果
     */
    private List<ArticleSearchDTO> searchArticle(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        // 添加文章标题高亮
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("name");
        titleField.preTags("<span style='color:#f47466'>");
        titleField.postTags("</span>");
        // 添加文章内容高亮
        HighlightBuilder.Field contentField = new HighlightBuilder.Field("content");
        contentField.preTags("<span style='color:#f47466'>");
        contentField.postTags("</span>");
        //显示文章全部内容
        contentField.numOfFragments(0);
        //显示除html标签外 200个字符
//        contentField.fragmentSize(200);
        nativeSearchQueryBuilder.withHighlightFields(titleField, contentField);
        // 搜索
        SearchHits<ArticleSearchDTO> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), ArticleSearchDTO.class);
        return search.getSearchHits().stream().map(hit -> {
            ArticleSearchDTO article = hit.getContent();
            // 获取文章标题高亮数据
            List<String> titleHighLightList = hit.getHighlightFields().get("name");
            if (CollectionUtils.isNotEmpty(titleHighLightList)) {
                // 替换标题数据
                article.setName(titleHighLightList.get(0));
            }
            // 获取文章内容高亮数据
            List<String> contentHighLightList = hit.getHighlightFields().get("content");
            if (CollectionUtils.isNotEmpty(contentHighLightList)) {
                // 替换内容数据
                article.setContent(contentHighLightList.get(0));
            }
            return article;
        }).collect(Collectors.toList());
    }


}
