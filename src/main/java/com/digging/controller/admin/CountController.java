package com.digging.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.digging.entity.User;
import com.digging.service.ArticleService;
import com.digging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CountController {

    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;

    //获取总注册用户数
    @GetMapping("/ucount")
    public int getUserCount()
    {
        return userService.count();
    }

    //获取总文章数
    @GetMapping("/bcount")
    public int getArticleCount()
    {
        return userService.count();
    }

    //获取总管理员数
    @GetMapping("/acount")
    public int getAdminCount()
    {
        return userService.count(new LambdaQueryWrapper<User>().eq(User::getIsAdmin, 1));
    }

}
