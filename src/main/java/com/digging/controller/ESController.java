//
//package com.digging.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.w3c.dom.Text;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Map;
//
//public class ESController {
//    @Autowired
//    private RestHighLevelClient client;
//    /**
//     * 搜索
//     *
//     * @param model model
//     * @param page  当前页码
//     * @return 模板路径/themes/{theme}/index
//     */
//
//    @GetMapping(value = "/search/page/{page}")
//    public String searchPage(Model model,
//                             @PathVariable(value = "page") Integer page,
//                             @RequestParam("keyword") String keyword) {
//        //默认显示20条
//        Integer size = 20;
//        //所有日志数据，分页
//        Page posts = new Page(page, size);
//        // Page<Post> posts = postService.searchByKeywords(HtmlUtil.escape(keyword), pageable);
//        page = page > 0 ? page : 0;
//        //search request
//        SearchRequest searchRequest = new SearchRequest("blog");
//        //search builder
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.matchQuery("postTitle", keyword));
//        sourceBuilder.from((page - 1) * size);
//        sourceBuilder.size(size);
//        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        //sort
//        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
//        //highlight
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("postTitle");
//        highlightTitle.preTags("<span class=\"highlight\">");
//        highlightTitle.postTags("</span>");
//        highlightBuilder.field(highlightTitle);
//        sourceBuilder.highlighter(highlightBuilder);
//        // add builder into request
//        searchRequest.indices("blog");
//        searchRequest.source(sourceBuilder);
//        //response
//        SearchResponse searchResponse = null;
//        try {
//            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        TimeValue took = searchResponse.getTook();
//        //search hits
//        SearchHits hits = searchResponse.getHits();
//        long totalHits = hits.getTotalHits();
//        SearchHit[] searchHits = hits.getHits();
//        List<EsPost> postList = new ArrayList<>();
//        posts.setTotal((int) totalHits);
//        for (SearchHit hit : searchHits) {
//            String str = hit.getSourceAsString();
//            EsPost esPost = JSONObject.parseObject(str, EsPost.class);
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            HighlightField highlight = highlightFields.get("postTitle");
//            if (highlight != null) {
//                Text[] fragments = highlight.fragments();
//                String fragmentString = fragments[0].string();
//                esPost.setPostTitle(fragmentString);
//            }
//            postList.add(esPost);
//        }
//        posts.setRecords(postList);
//        model.addAttribute("is_index", true);
//        model.addAttribute("posts", posts);
//        model.addAttribute("prefix", "/search");
//        model.addAttribute("suffix", "?keyword=" + keyword);
//        model.addAttribute("time", took);
//        return this.render("search");
//    }
//}
//
