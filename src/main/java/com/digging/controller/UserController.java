package com.digging.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.digging.common.MyCustomException;
import com.digging.common.Result;
import com.digging.entity.User;
import com.digging.model.dto.UserDTO;
import com.digging.service.ArticleService;
import com.digging.service.CommentService;
import com.digging.service.FollowService;
import com.digging.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;


    @PostMapping("/login")
    public Result<UserDTO> login(HttpServletRequest req, @RequestBody User user)
    {
        String username = user.getUsername();
        String password = user.getPassword();
        //对密码进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User userTmp = userService.getOne(queryWrapper);
        //若用户不存在
        if(userTmp == null)
        {
            return Result.error("用户名不存在！");
        }
        //若密码错误
        if(!userTmp.getPassword().equals(password))
        {
            return Result.error("密码错误！");
        }
        //判断用户是否禁用
        if(userTmp.getIsDisable())
        {
            return Result.error("用户已禁用！");
        }
        //登录成功
        req.getSession().setAttribute("user",userTmp.getId());
        req.getSession().setAttribute("username",username);

        log.info("{}", req.getSession().getAttribute("username"));

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userTmp,userDTO);
        return Result.success(userDTO ,"登录成功！");
    }

    @GetMapping("/logout")
    public Result<String> logout(HttpServletRequest httpServletRequest)
    {
        log.info("用户登出！");
        httpServletRequest.removeAttribute("user");
        httpServletRequest.removeAttribute("username");

        return Result.success("登出成功！");
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user)
    {
        User userTmp = new User();
        userTmp.setUsername(user.getUsername());
        //对密码进行MD5加密
        userTmp.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        if(userService.save(userTmp))
        {
            return Result.success("注册成功！");
        }
        else return Result.error("发生未知错误！");
    }

    //用户信息展示
    @GetMapping("/show")
    public Result<UserDTO> userShow(Long userId)
    {
        User user = userService.getById(userId);
        UserDTO userDTO = new UserDTO();

        if(user == null) throw new MyCustomException("用户ID为空！");
        BeanUtils.copyProperties(user, userDTO);

        //填充用户文章数和评论数
        userDTO.setArticleNum(articleService.getUserArticleNum(userId));
        userDTO.setCommentNum(commentService.getUserCommentNum(userId));

        userDTO.setFollowedNum(followService.getFollowedNum(userId));

        List<User> userList = userService.getFollowedUser(userId);
        List<UserDTO> userDTOList = new ArrayList<>();

        for(User item : userList)
        {
            UserDTO tmpUserDTO = new UserDTO();
            BeanUtils.copyProperties(item, tmpUserDTO);

            tmpUserDTO.setArticleNum(articleService.getUserArticleNum(item.getId()));
            tmpUserDTO.setCommentNum(commentService.getUserCommentNum(item.getId()));
            tmpUserDTO.setFollowedNum(followService.getFollowedNum(item.getId()));
            userDTOList.add(tmpUserDTO);
        }

        userDTO.setFollowedUser(userDTOList);

        System.out.println(userDTO);
        return Result.success(userDTO);
    }

    //用户修改个人信息
    @PostMapping("/update")
    public Result<String> updateUser(HttpServletRequest request, @RequestBody User user)
    {
        if(user.getUsername().equals("") || user.getPassword().equals(""))
        {
            return Result.error("用户名或密码不能为空！");
        }
        Long userId = (Long) request.getSession().getAttribute("user");
        //对密码进行MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setId(userId);
        //更新用户表
        userService.updateById(user);

        //更新文章表
        userService.updateArticleUsername(userId,user.getUsername());

        return Result.success("修改成功，请重新登录！");
    }
}
