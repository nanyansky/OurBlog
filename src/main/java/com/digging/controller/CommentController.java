package com.digging.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.digging.common.Result;
import com.digging.entity.Comment;
import com.digging.model.dto.CommentDTO;
import com.digging.service.CommentService;
import com.digging.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    //评论列表
    @GetMapping("/list")
    public Result<List<CommentDTO>> commentList(Long articleId)
    {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId).orderByDesc(Comment::getCreateTime);
        List<Comment> commentList = commentService.list(queryWrapper);

        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment item : commentList)
        {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(item,commentDTO);

            String username = userService.getById(item.getUserId()).getUsername();
            commentDTO.setUsername(username);
            String icon = userService.getById(item.getUserId()).getIcon();
            commentDTO.setIcon(icon);

            commentDTOList.add(commentDTO);
        }
        return Result.success(commentDTOList);
    }

    //发表评论
    @PostMapping("/add")
    public Result<String> addComment(HttpServletRequest request, @RequestBody CommentDTO commentDTO)
    {
        Long articleId = commentDTO.getArticleId();
        String comment = commentDTO.getComment();

        if(Objects.equals(comment, ""))
        {
            return Result.error("请输入评论内容再发送！");
        }
        Long userId = (Long) request.getSession().getAttribute("user");
        String username = (String) request.getSession().getAttribute("username");

        Comment commentTmp = new Comment();
        commentTmp.setArticleId(articleId);
        commentTmp.setComment(comment);
        commentTmp.setUserId(userId);

        commentService.save(commentTmp);

        return Result.success("发表成功！");
    }

}
