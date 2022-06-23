package com.digging.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Comment {
    //评论id
    private Long id;
    //评论用户id
    private Long userId;
    //所在文章id
    private Long articleId;
    //评论内容
    private String comment;
    //创建时间
    private LocalDateTime creatTime;
    //是否删除
    private Boolean isDelete;
}
