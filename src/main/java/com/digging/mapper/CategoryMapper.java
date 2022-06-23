package com.digging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digging.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
