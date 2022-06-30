package com.digging.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digging.annotation.OptLog;
import com.digging.common.Result;
import com.digging.entity.Article;
import com.digging.entity.User;
import com.digging.model.dto.PageDTO;
import com.digging.model.dto.UserDTO;
import com.digging.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    UserService userService;

    @OptLog(optType = "管理员登入")
    @PostMapping("/login")
    public Result<UserDTO> adminLongin(HttpServletRequest req, @RequestBody User user)
    {
        if(user.getUsername().equals("") || user.getPassword().equals("")) return Result.error("用户名和密码不能为空！");

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
        //判断用户是否为管理员
        if(!userTmp.getIsAdmin())
        {
            return Result.error("您没有权限！");
        }
        //登录成功
        req.getSession().setAttribute("user",userTmp.getId());
        req.getSession().setAttribute("username",username);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userTmp,userDTO);
        return Result.success(userDTO ,"登录成功！");
    }

    @OptLog(optType = "管理员登出")
    @GetMapping("/logout")
    public Result<String> logout(HttpServletRequest httpServletRequest)
    {
        log.info("用户登出！");
        httpServletRequest.removeAttribute("user");

        return Result.success("登出成功！");
    }

    //管理用户列表
    @GetMapping("/list")
    public Result<PageDTO> userList(int page, int pageSize, Long username)
    {
        log.info("page={}, pageSize={}",page,pageSize);

        Page pageInfo = new Page(page,pageSize);
        PageDTO pageDTO = new PageDTO();

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq((username != null),User::getUsername, username).orderByDesc(User::getCreateTime);

        userService.page(pageInfo, queryWrapper);

        pageDTO.setRecords(pageInfo.getRecords());
        pageDTO.setTotal(pageInfo.getTotal());

        return Result.success(pageDTO);
    }

    @GetMapping("/list")
    public Result<List<User>> articleList(int page, int pageSize)
    {
        return Result.success(userService.list());
    }

    @PostMapping("/update")
    public Result<String> updateUser(@RequestBody User user)
    {
        if (userService.updateById(user)) return Result.success("修改成功！");
        else return Result.error("未知错误！");
    }
}
