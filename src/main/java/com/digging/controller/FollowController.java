package com.digging.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.digging.annotation.OptLog;
import com.digging.common.Result;
import com.digging.entity.Follow;
import com.digging.entity.User;
import com.digging.model.dto.UserDTO;
import com.digging.service.FollowService;
import com.digging.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;


    //关注列表
    @GetMapping("/list")
    public Result<List<UserDTO>> followList(HttpServletRequest request)
    {
        Long userId = (Long) request.getSession().getAttribute("user");
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getGuestId, userId);

        List<Follow> followList = followService.list(queryWrapper);

        log.info("followList: {}", followList);

        List<UserDTO> userDTOList = new ArrayList<>();
        for(Follow item : followList)
        {
            UserDTO userDTO = new UserDTO();
            User user = userService.getById(item.getHostId());
            log.info("user:{}",user);
            BeanUtils.copyProperties(user,userDTO);

            userDTOList.add(userDTO);
        }

        return Result.success(userDTOList);
    }


    //关注功能
    @OptLog(optType = "关注用户")
    @GetMapping("/add/{hostUserId}")
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

    //取关功能
    @OptLog(optType = "取消关注用户")
    @GetMapping("/remove/{hostUserId}")
    public Result<String> unFollowUser(HttpServletRequest request, @PathVariable Long hostUserId)
    {
        Long userId = (Long) request.getSession().getAttribute("user");

        log.info("user:{}",userId);

        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getHostId, hostUserId);

        followService.remove(queryWrapper);


        return Result.success("取关成功！");
    }
}
