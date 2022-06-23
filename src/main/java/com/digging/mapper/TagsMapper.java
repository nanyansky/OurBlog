package com.digging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digging.entity.Tags;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagsMapper extends BaseMapper<Tags> {
    public List<Tags> getTagsList(Long articleId);
}
