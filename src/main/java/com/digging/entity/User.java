package com.digging.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    //用户id
    private Long id;
    //用户名
    private String username;
    //用户密码
    private String password;
    //用户头像地址
    private String icon;
    //是否管理员
    private Boolean isAdmin;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //是否禁用
    private Boolean isDisable;
}
