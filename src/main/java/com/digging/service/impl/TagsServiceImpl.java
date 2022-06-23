package com.digging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.Tags;
import com.digging.mapper.TagsMapper;
import com.digging.service.TagsService;
import org.springframework.stereotype.Service;

@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {
}
