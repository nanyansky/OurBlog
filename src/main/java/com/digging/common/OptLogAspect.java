package com.digging.common;
import com.digging.annotation.OptLog;
import com.digging.entity.OperationLogs;
import com.digging.mapper.OperationLogsMapper;
import com.digging.service.OperationLogsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class OptLogAspect {

    @Autowired
    OperationLogsService operationLogsService;
    @Autowired
    HttpServletRequest request;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.digging.annotation.OptLog)")
    public void optLogPointCut(){}

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    @AfterReturning(value = "optLogPointCut()", returning = "keys")
    public void saveOptLog(JoinPoint joinPoint, Object keys) {

        //获取用户名
        String username = (String) request.getSession().getAttribute("username");
        if(username == null) username = "游客";

        OperationLogs operationLogs = new OperationLogs();
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        OptLog optLog = method.getAnnotation(OptLog.class);

        // 操作用户
        operationLogs.setUsername(username);
        log.info("username:{}", username);
        // 操作类型
        operationLogs.setOperationType(optLog.optType());
        log.info("type:{}", optLog.optType());
        operationLogsService.save(operationLogs);
    }
}
