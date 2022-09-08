package com.digging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digging.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface LikeService {
    public void like(HttpServletRequest request, int entityType, int entityId);
    public long entityLikeCount (int entityType, int entityId);
    public int entityLikeStatue(HttpServletRequest request, int entityType, int entityId);
}
