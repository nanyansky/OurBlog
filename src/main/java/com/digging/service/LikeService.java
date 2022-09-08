package com.digging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digging.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface LikeService {
    /**
     * 点赞或取消
     * @param request
     * @param entityId
     */
    public void like(HttpServletRequest request, int entityType,int entityId);

    /**
     * 计算点赞数
     * @param entityId
     * @return
     */
    public long entityLikeCount (int entityType,int entityId);

    /**
     * 记录点赞状态
     * @param request
     * @param entityId
     * @return
     */
    public int entityLikeStatue(HttpServletRequest request,int entityType, int entityId);
    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();
}
