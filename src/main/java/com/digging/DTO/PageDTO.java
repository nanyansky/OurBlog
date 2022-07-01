package com.digging.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {
    /**
     * 分页列表
     */
    private List<T> records;

    /**
     * 总数
     */
    private Long total;

}
