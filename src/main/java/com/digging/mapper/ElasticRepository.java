package com.digging.mapper;

import com.alibaba.fastjson.JSONObject;
import com.digging.entity.DocBean;
import org.apache.ibatis.annotations.Mapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface ElasticRepository extends ElasticsearchRepository<DocBean, Long> {
    //默认的注释

    Page<DocBean> findByContent(String content, Pageable pageable);

//
//    public Page<DocBean> findByContent(String content) {
//
//        // 定义高亮字段
//        HighlightBuilder.Field contentField = new HighlightBuilder.Field("content").preTags("<span>").postTags("</span>");
//        // 构建查询内容
//        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(content);
//        // 查询匹配的字段
//        queryBuilder.field("content");
//
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
//                .withHighlightFields(contentField).build();
//        long count = elasticsearchTemplate.count(searchQuery, DocBean.class);
//        System.out.println("系统查询个数：--》" + count);
//        if (count == 0) {
//            return null;
//        }
//
//        AggregatedPage<DocBean> queryForPage = elasticsearchTemplate.queryForPage(searchQuery, DocBean.class,
//                new SearchResultMapper() {
//
//                    @Override
//                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
//                                                            Pageable pageable) {
//
//                        List<DocBean> list = new ArrayList<DocBean>();
//                        for (SearchHit searchHit : response.getHits()) {
//                            if (response.getHits().getHits().length <= 0) {
//                                return null;
//                            }
//                            DocBean DocBean = JSONObject.parseObject(searchHit.getSourceAsString(), DocBean.class);
//                            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
//                            //匹配到的content字段里面的信息
//                            HighlightField contentHighlight = highlightFields.get("content");
//                            if (contentHighlight != null) {
//                                Text[] fragments = contentHighlight.fragments();
//                                String fragmentString = fragments[0].string();
//                                DocBean.setContent(fragmentString);
//                            }
//                            list.add(DocBean);
//
//                        }
//                        if (list.size() > 0) {
//                            return new AggregatedPageImpl<T>((List<T>) list);
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
//                        return null;
//                    }
//                });
//        return queryForPage;
//    }


    Page<DocBean> findByFirstCode(String firstCode, Pageable pageable);

    Page<DocBean> findBySecordCode(String SecondCode, Pageable pageable);
}
