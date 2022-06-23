package com.digging.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.Category;
import com.digging.mapper.CategoryMapper;
import com.digging.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
