package com.digging.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLogs {
    //日志id
    private Long id;
    //操作用户id
    private String username;
    //操作类型
    private String operationType;
    //操作时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime operationTime;
}
