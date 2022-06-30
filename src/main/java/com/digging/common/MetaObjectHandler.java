package com.digging.common;

import com.digging.entity.Comment;
import com.digging.service.ArticleService;
import com.digging.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * 自动填充工具类
 */

@Component
@Slf4j
public class MetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充【insert】...");
        log.info(metaObject.toString());

        //自动填充创建时间
        if(metaObject.hasSetter("createTime"))
            metaObject.setValue("createTime", LocalDateTime.now());
        //自动填充跟更新时间
        if(metaObject.hasSetter("updateTime"))
            metaObject.setValue("updateTime", LocalDateTime.now());

        if(metaObject.hasSetter("operationTime"))
            metaObject.setValue("operationTime",LocalDateTime.now());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充【update】...");
        log.info(metaObject.toString());
        if(metaObject.hasSetter("updateTime"))
        {
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
    }
}
