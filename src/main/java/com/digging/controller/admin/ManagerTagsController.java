package com.digging.controller.admin;

import com.digging.common.Result;
import com.digging.entity.Tags;
import com.digging.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
public class ManagerTagsController {

    @Autowired
    TagsService tagsService;

    @GetMapping("/tagList")
    public Result<List<String>> getTagsList()
    {
        List<String> tagsList = new ArrayList<>();
        List<Tags> list = tagsService.list();
        for(Tags item : list)
        {
            tagsList.add(item.getTagName());
        }
        log.info("{}",tagsList);
        return Result.success(tagsList);
    }
}
