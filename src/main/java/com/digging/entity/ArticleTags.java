package com.digging.entity;

import lombok.Data;

@Data
public class ArticleTags {
    //id
    private Long id;
    //文章id
    private Long articleId;
    //标签id
    private Long tagId;

}
