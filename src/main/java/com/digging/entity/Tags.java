package com.digging.entity;

import lombok.Data;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Data
public class Tags {
    //标签id
    private Long id;
    //标签名
    private String tagName;

    //使用该标签的文章集合
//    private List<Long> articleIdList;
}
