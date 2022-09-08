package com.digging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.common.RedisKeyUtils;
import com.digging.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    RedisTemplate redisTemplate;
    //点赞
    public void like(HttpServletRequest request, int entityType, int entityId)
    {
        Long userId = (Long) request.getSession().getAttribute("user");

        String EntityKey = RedisKeyUtils.getEntityLikeKey(entityType,entityId);
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
    public long entityLikeCount(int entityType, int entityId)
    {
        String EntityKey = RedisKeyUtils.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().size(EntityKey);
    }

    //查询某人对某实体的点赞状态
    public int entityLikeStatue(HttpServletRequest request, int entityType, int entityId)
    {
        String EntityKey = RedisKeyUtils.getEntityLikeKey(entityType,entityId);
        Long userId = (Long) request.getSession().getAttribute("user");
        return redisTemplate.opsForSet().isMember(EntityKey,userId) ? 1 : 0;
    }
}
