package com.digging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digging.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    //获取用户评论数量
    @Select("select count(*) from comment where user_id = #{userId}")
    public Integer getUserCommentNum(Long userId);
}
