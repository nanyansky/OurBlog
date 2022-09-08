package com.digging.DTO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.digging.entity.Tags;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDTO {
    //文章id
    private Long id;
    //作者id
    private Long userId;
    //作者用户名
    private String username;
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
    //文章热度
    @Field(type = FieldType.Integer)
    private Long articleHot;
    //点赞数
    private int likeCount;


    //文章标签
    private List<Tags> tags = new ArrayList<>();

    //文章标签名
    private List<String> tagsName = new ArrayList<>();
}
