package com.digging.service.impl;

import com.digging.entity.DocBean;
import com.digging.mapper.ElasticRepository;
import com.digging.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ElasticServiceImpl implements ElasticService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private ElasticRepository elasticRepository;

    private Pageable pageable = PageRequest.of(0, 10);

    @Override
    public void createIndex() {
        elasticsearchTemplate.createIndex(DocBean.class);
        //创建mapping,使分词生效
        elasticsearchTemplate.putMapping(DocBean.class);
    }

    @Override
    public void deleteIndex(String index) {
        elasticsearchTemplate.deleteIndex(index);
    }

    @Override
    public void save(DocBean docBean) {
        elasticRepository.save(docBean);
    }

    @Override
    public void saveAll(List<DocBean> list) {
        elasticRepository.saveAll(list);
    }

    @Override
    public Iterator<DocBean> findAll() {
        return elasticRepository.findAll().iterator();
    }

    @Override
    public Page<DocBean> findByContent(String content) {
        return elasticRepository.findByContent(content, pageable);
    }

    @Override
    public Page<DocBean> findByFirstCode(String firstCode) {
        return elasticRepository.findByFirstCode(firstCode, pageable);
    }

    @Override
    public Page<DocBean> findBySecondCode(String SecondCode) {
        return elasticRepository.findBySecordCode(SecondCode, pageable);
    }

    @Override
    public Page<DocBean> query(String key) {
        return elasticRepository.findByContent(key, pageable);
    }
}
