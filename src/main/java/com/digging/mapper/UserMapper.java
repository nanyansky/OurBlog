package com.digging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digging.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface UserMapper extends BaseMapper<User> {
    //查找粉丝数
    List<User> getFollowedUser(Long userId);
}
