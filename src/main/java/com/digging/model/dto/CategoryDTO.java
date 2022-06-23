package com.digging.model.dto;

import com.digging.entity.Article;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryDTO {
    //标签id
    private Long id;
    //标签名
    private String name;
    //创建日期
    private LocalDateTime date;

    //该分类下的文章集合
    private List<ArticleDTO> articleList;
}
