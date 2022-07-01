package com.digging.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * 文章搜索结果
 *
 * @author 11921
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "article")
public class ArticleSearchDTO {

    /**
     * 文章id
     */
    @Id
    private Long id;

    /**
     * 文章标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String name;

    /**
     * 文章内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;

    /**
     * 文章状态
     */
    @Field(type = FieldType.Integer)
    private Integer available;


    //文章作者username
    private String username;
    //文章作者id
    private Long userId;
    //文章创建时间
    private LocalDateTime createTime;
    //文章修改时间
    private LocalDateTime updateTime;

}