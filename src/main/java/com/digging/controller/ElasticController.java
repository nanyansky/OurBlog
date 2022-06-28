package com.digging.controller;

import com.digging.entity.DocBean;
import com.digging.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/elastic")
public class ElasticController {

    @Autowired
    private ElasticService elasticService;

    @GetMapping("/init")
    public void init() {
        elasticService.createIndex();
        List<DocBean> list = new ArrayList<>();
        list.add(new DocBean(1L, "XX0193", "XX8064", "中国", 1));
        list.add(new DocBean(2L, "XX0210", "XX7475", "我是一个中国人", 1));
        list.add(new DocBean(3L, "XX0257", "XX8097", "我爱中华人民共和国", 1));
        elasticService.saveAll(list);

    }

    @GetMapping("/all")
    public Iterator<DocBean> all() {
        return elasticService.findAll();
    }

    @GetMapping("/findByContent")
    public Page<DocBean> findByContent(String content) {
        Page<DocBean> x = elasticService.findByContent(content);
        return x;
    }


    @GetMapping("/findByFirstCode")
    public Page<DocBean> findByFirstCode() {
        return elasticService.findByFirstCode("XX0193");
    }

    @GetMapping("/findBySecondCode")
    public Page<DocBean> findBySecondCode() {
        return elasticService.findBySecondCode("XX7475");
    }
}
