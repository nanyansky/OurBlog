package com.digging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.OperationLogs;
import com.digging.mapper.OperationLogsMapper;
import com.digging.service.OperationLogsService;
import org.springframework.stereotype.Service;

@Service
public class OperationLogsServiceImpl extends ServiceImpl<OperationLogsMapper, OperationLogs> implements OperationLogsService {
}
