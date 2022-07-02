package com.digging.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.digging.common.Result;
import com.digging.entity.OperationLogs;
import com.digging.service.OperationLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ManagerLogsController {

    @Autowired
    OperationLogsService operationLogsService;

    @GetMapping("/logsList")
    public Result<List<OperationLogs>> LogsList()
    {
        return Result.success(operationLogsService.list(new LambdaQueryWrapper<OperationLogs>().orderByDesc(OperationLogs::getOperationTime)));
    }
}
