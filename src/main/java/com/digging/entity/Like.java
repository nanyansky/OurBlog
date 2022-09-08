package com.digging.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Like implements Serializable {
    //实体类型
    int entityType;
    //实体Id
    int entityId;
}
