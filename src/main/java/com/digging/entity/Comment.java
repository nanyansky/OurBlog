package com.digging.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //是否删除
    private Boolean isDelete;
    //点赞数
    private int likeCount;
}
