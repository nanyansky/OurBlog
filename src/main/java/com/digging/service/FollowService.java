package com.digging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digging.entity.Follow;
import com.digging.DTO.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowService extends IService<Follow> {
    //获取粉丝数量
    public Integer getFollowedNum(Long userId);
    //获取粉丝列表
    public List<UserDTO> getFollowedUser(Long userId);
}
