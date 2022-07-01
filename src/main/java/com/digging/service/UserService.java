package com.digging.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digging.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends IService<User> {
    //查找粉丝数
    List<User> getFollowedUser(Long userId);

    //更新文章表
    void updateArticleUsername(Long userId,String username);
}
