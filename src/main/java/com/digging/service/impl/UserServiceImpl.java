package com.digging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.User;
import com.digging.mapper.UserMapper;
import com.digging.model.dto.UserDTO;
import com.digging.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public List<User> getFollowedUser(Long userId) {
        return userMapper.getFollowedUser(userId);
    }
}
