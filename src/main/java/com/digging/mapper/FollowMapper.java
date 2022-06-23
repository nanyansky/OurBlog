package com.digging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digging.entity.Follow;
import com.digging.entity.User;
import com.digging.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowMapper extends BaseMapper<Follow> {
    //获取粉丝数量
    @Select("select count(*) from follow where host_id = #{userId}")
    public Integer getFollowedNum(@Param("userId") Long userId);
    //获取粉丝列表

    public List<UserDTO> getFollowedUser(@Param("userId") Long userId);
}
