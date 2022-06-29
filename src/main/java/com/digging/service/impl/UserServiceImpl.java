package com.digging.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digging.entity.Article;
import com.digging.entity.User;
import com.digging.mapper.UserMapper;
import com.digging.model.dto.UserDTO;
import com.digging.service.ArticleService;
import com.digging.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    BaseMapper baseMapper;
    @Resource
    ArticleService articleService;

    @Override
    public List<User> getFollowedUser(Long userId) {
        return userMapper.getFollowedUser(userId);
    }

    @Override
    public void updateArticleUsername(Long userId,String username) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getUserId, userId);

        List<Article> userList = articleService.list(queryWrapper);

        for(Article item : userList)
        {
            Article article = new Article();
            BeanUtils.copyProperties(item,article);
            article.setUsername(username);

            articleService.updateById(article);
        }
    }
}
