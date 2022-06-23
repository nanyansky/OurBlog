package com.digging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.Comment;
import com.digging.mapper.CommentMapper;
import com.digging.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    CommentMapper commentMapper;

    @Override
    public Integer getUserCommentNum(Long userId) {
        return commentMapper.getUserCommentNum(userId);
    }
}
