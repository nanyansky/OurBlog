package com.digging.entity;

import lombok.Data;

@Data
public class OperationLogs {
    //日志id
    private Long id;
    //操作用户id
    private Long userId;
    //操作类型
    private String operationLogs;
}
