package com.digging.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digging.common.Result;
import com.digging.entity.Article;
import com.digging.entity.Category;
import com.digging.entity.Tags;
import com.digging.model.dto.ArticleDTO;
import com.digging.model.dto.PageDTO;
import com.digging.service.ArticleService;
import com.digging.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @Autowired
    TagsService tagsService;

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

    //添加文章
    @PostMapping("/add")
    public Result<ArticleDTO> addBlog(HttpServletRequest request, @RequestBody ArticleDTO articleDTO)
    {
        Long userId = (Long) request.getSession().getAttribute("user");
        List<String> tagsList = articleDTO.getTagsName();
        articleDTO.setUserId(userId);
        System.out.println(articleDTO.getTagsName());
//        log.info("tags: {}", articleDTO);

        for(String tag : tagsList)
        {

        }

        return Result.success(articleDTO);

    }

}

