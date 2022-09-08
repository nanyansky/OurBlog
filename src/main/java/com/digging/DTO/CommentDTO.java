package com.digging.DTO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    //评论id
    private Long id;
    //评论用户id
    private Long userId;
    //评论用户username
    private String username;
    //评论用户头像
    private String icon;
    //所在文章id
    private Long articleId;
    //评论内容
    private String comment;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //点赞数
    private int likeCount;
}
