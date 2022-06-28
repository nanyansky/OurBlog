package com.digging.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data

//需要使用Document注解，indexName:库的名称，type:存储类型，一般使用bean的名称，
//分片（Shard）以及副本（Replica），分布式存储系统为了解决单机容量以及容灾的问题，都需要有分片以及副本机制。
@Document(indexName = "ems")
public class DocBean {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String firstCode;

    @Field(type = FieldType.Keyword)
    private String secordCode;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Integer)
    private Integer type;

    public DocBean(Long id, String firstCode, String secordCode, String content, Integer type) {
        this.id = id;
        this.firstCode = firstCode;
        this.secordCode = secordCode;
        this.content = content;
        this.type = type;
    }
}
