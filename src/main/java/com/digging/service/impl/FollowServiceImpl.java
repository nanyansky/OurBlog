package com.digging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.Follow;
import com.digging.mapper.FollowMapper;
import com.digging.DTO.UserDTO;
import com.digging.service.FollowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    @Resource
    FollowMapper followMapper;

    @Override
    public Integer getFollowedNum(Long userId) {
        return followMapper.getFollowedNum(userId);
    }

    @Override
    public List<UserDTO> getFollowedUser(Long userId) {
        return followMapper.getFollowedUser(userId);
    }
}
