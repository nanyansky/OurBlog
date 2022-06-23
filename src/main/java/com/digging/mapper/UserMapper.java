package com.digging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digging.entity.User;
import com.digging.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    //查找粉丝数
    List<User> getFollowedUser(Long userId);
}
