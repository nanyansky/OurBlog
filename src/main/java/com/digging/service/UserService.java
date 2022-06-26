package com.digging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digging.entity.User;
import com.digging.model.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface UserService extends IService<User> {
    //查找粉丝数
    List<User> getFollowedUser(Long userId);
}
