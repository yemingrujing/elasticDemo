package com.test.elasticsearch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.test.elasticsearch.service.AddressRegionService;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AddressRegionServiceImpl implements AddressRegionService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public String queryAddressRegionById(String id) {
        GetRequest request = new GetRequest("cmscontent", id);
        try {
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            return response.getSourceAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List queryScrollSearch() {
        SearchRequest request = new SearchRequest("cmscontent");
        request.scroll(TimeValue.timeValueMinutes(1L));
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 设置每批读取的数据量
        builder.size(100);
        builder.fetchSource(true);
        // 查询条件
        builder.query(QueryBuilders.matchAllQuery());
        // 允许搜索的时间。
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            List<JSONObject> list = getScrollHitString(response);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List queryTemplateSearch(Long id, Long provinceId, Long parentId,String name) {
        SearchTemplateRequest request = new SearchTemplateRequest();
        SearchRequest searchRequest = new SearchRequest("cmscontent");
        request.setRequest(searchRequest);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 设置每批读取的数据量
        builder.size(100);
        builder.fetchSource(true);
        // 允许搜索的时间。
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(builder);
        request.setScriptType(ScriptType.INLINE);
        Map<String, Object> scriptParams = new HashMap<>();
        JSONObject must = new JSONObject();
        JSONObject script;
        if(id != null || provinceId != null || parentId != null || (name != null && name.trim().length() > 0)) {
            if (id != null) {
                JSONObject match = new JSONObject();
                match.put("id", "{{id}}");
                scriptParams.put("id", id);
                must.put("match", match);
            }
            if (provinceId != null) {
                JSONObject match = new JSONObject();
                match.put("province_id", "{{provinceId}}");
                scriptParams.put("provinceId", provinceId);
                must.put("match", match);
            }
            if (parentId != null) {
                JSONObject match = new JSONObject();
                match.put("parent_id", "{{parentId}}");
                scriptParams.put("parentId", parentId);
                must.put("match", match);
            }
            if (name != null && name != "") {
                JSONObject match = new JSONObject();
                match.put("name", "{{name}}");
                scriptParams.put("name", name);
                must.put("match", match);
            }
            JSONObject bool = new JSONObject().fluentPut("must", must);
            script = new JSONObject().fluentPut("query", new JSONObject().fluentPut("bool", bool));
        } else {
            JSONObject match = new JSONObject();
            match.put("boost", "{{boost}}");
            scriptParams.put("boost", 1.0);
            script = new JSONObject().fluentPut("query", new JSONObject().fluentPut("match_all", match));
        }

        request.setScript(JSONObject.toJSONString(script));
        request.setScriptParams(scriptParams);
        try {
            Long startTime = System.currentTimeMillis();
            SearchTemplateResponse searchTemplateResponse = restHighLevelClient.searchTemplate(request, RequestOptions.DEFAULT);
            SearchResponse response = searchTemplateResponse.getResponse();
            List<JSONObject> list = new ArrayList<>();
            SearchHit[] searchHits = response.getHits().getHits();
            for (SearchHit searchHit : searchHits) {
                list.add(JSONObject.parseObject(searchHit.getSourceAsString()));
            }
            Long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List getScrollHitString(SearchResponse response) throws IOException {
        List<JSONObject> list = new ArrayList<>();
        SearchHit[] searchHits = response.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            list.add(JSONObject.parseObject(searchHit.getSourceAsString()));
        }
        Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        String scrollId = response.getScrollId();
        SearchResponse searchResponse;
        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
            for (SearchHit searchHit : searchHits) {
                list.add(JSONObject.parseObject(searchHit.getSourceAsString()));
            }
        }
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        boolean succeeded = clearScrollResponse.isSucceeded();
        if (succeeded) {
            return list;
        }
        return null;
    }

    @Override
    public ResponseEntity<String> queryBySql(String sql) throws UnsupportedEncodingException {
//        Request request = new Request("post", new StringBuilder("_xpack/sql").toString());
//        // 添加格式优化
//        request.addParameter("format", "json");
//
//        // 拼装body数据
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("query", sql);
//        request.setEntity(new NStringEntity(jsonObject.toJSONString(), ContentType.APPLICATION_JSON.withCharset(CharsetUtil.CHARSET_UTF_8)));
//        Response response;
//        String responseBody = null;
//        try {
//            response = restClient.performRequest(request);
//            responseBody = EntityUtils.toString(response.getEntity(), CharsetUtil.CHARSET_UTF_8);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(responseBody, HttpStatus.OK);
        return null;
    }
}
