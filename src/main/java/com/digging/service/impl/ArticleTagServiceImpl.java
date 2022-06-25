package com.digging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.ArticleTags;
import com.digging.mapper.ArticleTagMapper;
import com.digging.service.ArticleService;
import com.digging.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTags> implements ArticleTagService {
}
