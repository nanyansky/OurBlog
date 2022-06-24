package com.digging.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    //标签id
    private Long id;
    //标签名
    private String name;
    //创建日期
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
