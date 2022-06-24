package com.digging.controller;

import com.digging.common.Result;
import com.digging.entity.Follow;
import com.digging.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    FollowService followService;

    //关注
    @GetMapping("/{hostUserId}")
    public Result<String> followUser(HttpServletRequest request, @PathVariable Long hostUserId)
    {
        Long userId = (Long) request.getSession().getAttribute("user");

        log.info("user:{}",userId);

        Follow follow = new Follow();
        follow.setGuestId(userId);
        follow.setHostId(hostUserId);

        followService.save(follow);


        return Result.success("关注成功！");
    }
}
