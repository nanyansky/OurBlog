package com.digging.controller;

import com.digging.annotation.OptLog;
import com.digging.common.Result;
import com.digging.entity.Like;
import com.digging.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class LikeController {
    @Autowired
    LikeService likeService;
    @Autowired
    HttpServletRequest request;


    @OptLog(optType = "用户点赞")
    @GetMapping("/like")
    public Result<Map<String,Object>> like(@RequestParam int entityId,@RequestParam int entityType)
    {
        //点赞
        likeService.like(request,entityType,entityId);
        //数量
        long likeCount = likeService.entityLikeCount(entityType,entityId);
        //状态
        int likeStatue = likeService.entityLikeStatue(request,entityType,entityId);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("likeCount",likeCount);
        resultMap.put("likeStatus",likeStatue);

        return Result.success(resultMap,"操作成功！");
    }
}
