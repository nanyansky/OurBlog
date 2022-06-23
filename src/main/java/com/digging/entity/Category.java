package com.digging.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    //标签id
    private Long id;
    //标签名
    private String name;
    //创建日期
    private LocalDateTime date;
}
