package com.digging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digging.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService extends IService<Comment> {
    //获取用户评论数量
    public Integer getUserCommentNum(Long userId);
}
