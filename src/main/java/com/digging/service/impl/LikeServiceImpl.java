package com.digging.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.digging.entity.Article;
import com.digging.entity.Comment;
import com.digging.service.ArticleService;
import com.digging.service.CommentService;
import com.digging.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;


    //点赞
    public void like(HttpServletRequest request,int entityType, int entityId)
    {
        Long userId = (Long) request.getSession().getAttribute("user");

        String EntityKey = entityId+ "_" + entityType;
        boolean isNumber = redisTemplate.opsForSet().isMember(EntityKey,userId);
        if(isNumber)
        {
            redisTemplate.opsForSet().remove(EntityKey,userId);
        }
        else
        {
            redisTemplate.opsForSet().add(EntityKey,userId);
        }
    }

    //查询点赞数量
    public long entityLikeCount(int entityType,int entityId)
    {
        String EntityKey = entityId+ "_" + entityType;
        return redisTemplate.opsForSet().size(EntityKey);
    }

    //查询某人对某实体的点赞状态
    public int entityLikeStatue(HttpServletRequest request,int entityType, int entityId)
    {
        String EntityKey = entityId+ "_" + entityType;
        Long userId = (Long) request.getSession().getAttribute("user");
        return redisTemplate.opsForSet().isMember(EntityKey,userId) ? 1 : 0;
    }

    @Override
    public void transLikedCountFromRedis2DB() {
        Set keys = redisTemplate.keys("*");
        for(Object key : keys)
        {
            String tmpKey = ((String)key).split("_")[0];
            //点赞文章
            if(((String)key).endsWith("0"))
            {
                LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(Article::getId,Long.valueOf(tmpKey)).set(Article::getLikeCount,Math.toIntExact(redisTemplate.opsForSet().size(key)));
                articleService.update(updateWrapper);
            }
            //点赞评论
            if(((String)key).endsWith("1"))
            {
                LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(Comment::getId,Long.valueOf(tmpKey)).set(Comment::getLikeCount,Math.toIntExact(redisTemplate.opsForSet().size(key)));
                commentService.update(updateWrapper);
            }
        }
    }
}
