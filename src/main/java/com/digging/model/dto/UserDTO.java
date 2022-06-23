package com.digging.model.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.digging.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    //用户id
    private Long id;
    //用户名
    private String username;
    //用户头像地址
    private String icon;

    //用户文章数
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer articleNum;
    //用户评论数
    @TableField(fill = FieldFill.INSERT)
    private Integer commentNum;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //粉丝数
    private Integer followedNum;
    //粉丝列表
    private List<UserDTO> followedUser;
}
