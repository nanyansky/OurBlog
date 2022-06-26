package com.digging.controller;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digging.common.Result;
import com.digging.entity.Article;
import com.digging.entity.ArticleTags;
import com.digging.model.dto.ArticleDTO;
import com.digging.model.dto.PageDTO;
import com.digging.service.ArticleService;
import com.digging.service.ArticleTagService;
import com.digging.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/blog")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    TagsService tagsService;
    @Autowired
    ArticleTagService articleTagService;

    /**
     * 首页：文章分页
     * @param page
     * @param pageSize
     * @return 分页文章
     */
    @GetMapping("/list")
    public Result<PageDTO> blogList(int page, int pageSize, Long userId)
    {
        log.info("page={}, pageSize={}",page,pageSize);
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        PageDTO pageDTO = new PageDTO<>();
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //添加个人文章列表，若存在userId参数，则说明查询个人文章列表
        queryWrapper.eq((userId != null), Article::getUserId,userId);

        queryWrapper.orderByDesc(Article::getUpdateTime);
        articleService.page(pageInfo,queryWrapper);


        //拷贝Article属性拷贝到ArticleDTO
        List<Article> articleList = pageInfo.getRecords();
        //转换
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for(Article item : articleList)
        {
            ArticleDTO articleDTO = new ArticleDTO();
            //拷贝数据
            BeanUtils.copyProperties(item, articleDTO, "available","isDelete");
            articleDTO.setTags(articleService.getTagsList(item.getId()));
            System.out.println(articleService.getTagsList(item.getId()));
            articleDTOList.add(articleDTO);
        }


        //只传输必要的数据
        pageDTO.setRecords(articleDTOList);
        pageDTO.setTotal(pageInfo.getTotal());

        return Result.success(pageDTO);
    }

    //文章时间轴
    @GetMapping("/blogTime")
    public Result<List<ArticleDTO>> blogTime(Long userId)
    {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查找用户所有文章并a按创建日期降序排序
        queryWrapper.eq(Article::getUserId, userId).orderByDesc(Article::getCreateTime);

        //entity转DTO
        List<Article> articleList = articleService.list(queryWrapper);
        System.out.println(articleList);
        List<ArticleDTO> articleDTOList = new ArrayList<>();

        //拷贝数据
        for(Article item : articleList)
        {
            ArticleDTO articleDTO = new ArticleDTO();

            BeanUtils.copyProperties(item,articleDTO);

            articleDTO.setTags(articleService.getTagsList(item.getId()));

            articleDTOList.add(articleDTO);
        }

        return Result.success(articleDTOList,"请求成功！");
    }

    //修改文章
    @PostMapping("/update")
    public Result<Article> updateBlog(HttpServletRequest request, @RequestBody ArticleDTO articleDTO)
    {
        Long articleId = articleDTO.getId();
        Long userId = (Long) request.getSession().getAttribute("user");
        List<String> tagsList = articleDTO.getTagsName();
        log.info("tagsList: {}",tagsList);
        articleDTO.setUserId(userId);

        //修改文章
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO,article);

        articleService.updateById(article);

        //处理标签
        articleService.handleTags(articleId,tagsList);

//        return Result.success("修改成功！");
        return Result.success(article);
    }

    //添加文章
    @PostMapping("/add")
    public Result<String> addBlog(HttpServletRequest request, @RequestBody ArticleDTO articleDTO)
    {
        //雪花算法生成 articleId
        Snowflake snowflake = new Snowflake(1,1);
        long articleId = snowflake.nextId();

        Long userId = (Long) request.getSession().getAttribute("user");
        List<String> tagsList = articleDTO.getTagsName();
        articleDTO.setUserId(userId);
        articleDTO.setId(articleId);

        //保存文章
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO,article);
        articleService.save(article);

        log.info("tags:{}",tagsList);

        //处理标签
        articleService.handleTags(articleId,tagsList);

        return Result.success("发布成功，请等待审核！");
    }



    //删除文章
    @GetMapping("/delete")
    public Result<String> deleteBlog(HttpServletRequest request, Long articleId)
    {
        Long userId = (Long) request.getSession().getAttribute("user");
        articleService.getById(new LambdaQueryWrapper<Article>().select(Article::getId).eq(Article::getId, articleId));
        if(!Objects.equals(articleService.getOne(new LambdaQueryWrapper<Article>().select(Article::getUserId).eq(Article::getId, articleId)), userId))
        {
            return Result.error("您不是此文章的作者，无法删除！");
        }

        articleService.removeById(articleId);

        articleTagService.remove(new LambdaQueryWrapper<ArticleTags>().eq(ArticleTags::getArticleId, articleId));

        return Result.success("删除成功！");
    }
}

