package com.digging.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Article {
    //文章id
    private Long id;
    //作者id
    private Long userId;
    //作者用户名
    private String username;
    //是否通过审核
    private Boolean available;
    //分类id
    private Long categoryId;
    //文章名
    private String name;
    //文章内容
    private String content;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //是否被删除
    private Boolean isDelete;
    //文章热度
    private Long articleHot;
    //点赞数
    private int likeCount;


    //文章标签
//    private List<Tags> tags = new ArrayList<>();

}
