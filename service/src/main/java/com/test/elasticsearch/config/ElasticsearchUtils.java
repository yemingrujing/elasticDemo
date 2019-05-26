package com.test.elasticsearch.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.elasticsearch.utils.PageBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.config
 * @ClassName: ElasticsearchUtils
 * @Author: guang
 * @Description: ES 工具类
 * https://blog.csdn.net/u010177412/article/details/82835230
 * @Date: 2019/5/10 17:06
 * @Version: 1.0
 */
public class ElasticsearchUtils {

    @Autowired
    private RestHighLevelClient elasticClient;

    private static final String INDEX_KEY = "index";

    /**
     * 类型
     */
    private static final String TYPE = "doc";

    private static final String INDEX = "cmscontent";

    /**
     * 时间类型
     */
    private static final String TIMESTAMP = "timestamp";

    /**
     * 创建索引(指定 index 和 type
     * @param indexName
     */
    public void createIndex(String indexName) throws IOException {
        if (!checkIndexExists(indexName)) {
            System.out.println("\"index={}\"索引已经存在！" + indexName);
            return;
        }
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.mapping(generateBuilder());
        CreateIndexResponse response = elasticClient.indices().create(request, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = response.isAcknowledged();
        // 指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        if (acknowledged || shardsAcknowledged) {
            System.out.println("创建索引成功！索引名称为{}：" + indexName);
        }
    }

    /**
     * 创建索引(传入参数：分片数、副本数)
     * @param indexName
     * @param shards
     * @param replicas
     */
    public void createIndex(String indexName, int shards, int replicas) throws IOException {
        if (!checkIndexExists(indexName)) {
            System.out.println("\"index={}\"索引已经存在！" + indexName);
            return;
        }
        Builder builder = Settings.builder().put("index.number_of_shards", shards).put("index.number_of_replicas", replicas);
        CreateIndexRequest request = new CreateIndexRequest(indexName).settings(builder);
        request.mapping(generateBuilder());
        CreateIndexResponse response = elasticClient.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        if (acknowledged || shardsAcknowledged) {
            System.out.println("创建索引成功！索引名称为{}：" + indexName);
        }
    }

    /**
     * 删除索引
     * @param indexName
     */
    public void deleteIndex(String indexName) throws IOException {
        try {
            AcknowledgedResponse response = elasticClient.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
            if (response.isAcknowledged()) {
                System.out.println("索引删除成功！索引名称为{}: " + indexName);
            }
        } catch (ElasticsearchException  e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("索引名不存在{}：" + indexName);
            }
            System.out.println("删除失败！");
        }
    }

    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     */
    public boolean checkIndexExists(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        try {
            return elasticClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            System.out.println("判断索引是否存在,操作异常！");
        }
        return false;
    }

    /**
     * 开启索引
     * @param indexName
     */
    public void openIndex(String indexName) throws IOException {
        if (!checkIndexExists(indexName)) {
            System.out.println("\"index={}\"索引已经存在! " + indexName);
            return;
        }
        OpenIndexResponse response = elasticClient.indices().open(new OpenIndexRequest(indexName), RequestOptions.DEFAULT);
        if (response.isAcknowledged() || response.isShardsAcknowledged()) {
            System.out.println("索引开启成功！索引名称：" + indexName);
        }
    }

    /**
     * 关闭索引
     * @param indexName
     */
    public void closeIndex(String indexName) throws IOException {
        if (!checkIndexExists(indexName)) {
            System.out.println("\"index={}\"索引已经存在! " + indexName);
            return;
        }
        AcknowledgedResponse response = elasticClient.indices().close(new CloseIndexRequest(indexName), RequestOptions.DEFAULT);
        if (response.isAcknowledged()) {
            System.out.println("索引已关闭！索引名称为{}: " + indexName);
        }
    }

    /**
     * 设置文档的静态映射(主要是为 message字段 设置ik分词器)
     * @param indexName
     */
    public void setFieldMapping(String indexName) {
        try {
            AcknowledgedResponse response = elasticClient.indices().putMapping(new PutMappingRequest(indexName).source(generateBuilder()), RequestOptions.DEFAULT);
            if (response.isAcknowledged()) {
                System.out.println("已成功对\"index={}\"的文档设置类型映射：" + indexName);
            }
        } catch (IOException e) {
            System.out.println("\"index={}\"的文档设置类型映射失败，请检查参数：" + indexName);
        }

    }

    /**
     * 增加文档
     * @param indexName
     * @param id
     * @param jsonString
     */
    public void addDocByJson(String indexName, String id, String jsonString) throws IOException {
        if (!isJSONValid(jsonString)) {
            System.out.println("非法的json字符串，操作失败！");
            return;
        }
        if (!checkIndexExists(indexName)) {
            createIndex(indexName);
        }
        IndexRequest request = new IndexRequest(indexName).id(id).source(jsonString, XContentType.JSON);
        IndexResponse response;
        try {
            response = elasticClient.index(request, RequestOptions.DEFAULT);
            String index = response.getIndex();
            String documentId = response.getId();
            if (response.getResult() == DocWriteResponse.Result.CREATED) {
                System.out.println("修改文档成功！index："+ index +"，id：" +  documentId);
            } else if (response.getResult() == DocWriteResponse.Result.UPDATED){
                System.out.println("修改文档成功！index："+ index +"，id：" +  documentId);
            }

            // 分片处理信息
            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                System.out.println("文档未写入全部分片副本！");
            }
            // 如果有分片副本失败，可以获得失败原因信息
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    System.out.println("副本失败原因{}：" + reason);
                }
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("版本异常！");
            }
            System.out.println("文档新增失败！");
        }
    }

    /**
     * 查找文档
     * @param indexName
     * @param id
     * @return
     */
    public Map<String, Object> getDocument(String indexName, String id) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        GetRequest request = new GetRequest(indexName).id(id);
        // 实时(否)
        request.realtime(false);
        // 检索之前执行刷新(是)
        request.refresh(true);

        GetResponse response = null;
        try {
            response = elasticClient.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException  e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("文档未找到，请检查参数！");
            } else if (e.status() == RestStatus.CONFLICT) {
                System.out.println("版本冲突！");
            }
            System.out.println("文档查找失败，请检查参数！");
        }

        if (Objects.nonNull(request)) {
            // 文档存在
            if (response.isExists()) {
                resultMap = response.getSourceAsMap();
            } else {
                // 处理未找到文档的方案。 请注意，虽然返回的响应具有404状态代码，但仍返回有效的GetResponse而不是抛出异常
                // 此时此类响应不持有任何源文档，并且其isExists方法返回false。
                System.out.println("文档未找到，请检查参数！");
            }
        }
        return resultMap;
    }

    /**
     * 删除文档
     * @param indexName
     * @param id
     */
    public void deleteDocument(String indexName, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(indexName).id(id);
        DeleteResponse response = null;
        try {
            response = elasticClient.delete(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException  e) {
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("版本冲突！");
            }
            System.out.println("文档删除失败，请检查参数！");
        }

        if (Objects.nonNull(response)) {
            if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
                System.out.println("不存在该文档，请检查参数！");
            }
            System.out.println("文档已删除！");
            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getTotal()) {
                System.out.println("部分分片副本未处理！");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    System.out.println("失败原因{}：" + reason);
                }
            }
        }
    }

    /**
     * 通过一个脚本语句(如："ctx._source.posttime=\"2018-09-18\"")更新文档
     * @param indexName
     * @param id
     * @param script
     */
    public void updateDocByScript(String indexName, String id, String script) throws IOException {
        Script inline = new Script(script);
        UpdateRequest request = new UpdateRequest(indexName, id).script(inline);
        try {
            UpdateResponse response = elasticClient.update(request, RequestOptions.DEFAULT);
            if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                System.out.println("文档更新成功！");
            } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
                System.out.println("\"index="+ response.getIndex() +",id="+ response.getId() +"\"的文档已被删除，无法更新！");
            } else {
                System.out.println("操作没有被执行！");
            }

            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                System.out.println("部分分片副本未处理");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    System.out.println("未处理原因{}：" + reason);
                }
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("不存在这个文档，请检查参数！");
            } else if (e.status() == RestStatus.CONFLICT) {
                System.out.println("版本冲突异常！");
            }
            System.out.println("更新失败！");
        }
    }

    /**
     * 通过一个JSON字符串更新文档(如果该文档不存在，则根据参数创建这个文档)
     * @author  GuangWei
     * @param indexName
     * @param id
     * @param jsonString
     * @return  void
     * @exception  
     * @date       2019/5/19 12:02
     */
    public void updateDocByJson(String indexName, String id, String jsonString) throws IOException {
        if (!isJSONValid(jsonString)) {
            System.out.println("非法的json字符串，操作失败！");
        }
        if (!checkIndexExists(indexName)) {
            createIndex(indexName);
        }
        UpdateRequest request = new UpdateRequest(indexName, id);
        request.doc(jsonString, XContentType.JSON);
        // 如果要更新的文档不存在，则根据传入的参数新建一个文档
        request.docAsUpsert(true);
        try {
            UpdateResponse response = elasticClient.update(request, RequestOptions.DEFAULT);
            String index = response.getIndex();
            String documentId = response.getId();
            if (response.getResult() == DocWriteResponse.Result.CREATED) {
                System.out.println("文档新增成功！index：" + index + ",id：" + documentId);
            } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                System.out.println("文档更新成功！");
            } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
                System.out.println("index=" + index + ",id=" + documentId + "的文档已被删除，无法更新！");
            } else if (response.getResult() == DocWriteResponse.Result.NOOP) {
                System.out.println("操作没有被执行！");
            }

            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                System.out.println("分片副本未全部处理");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    System.out.println("未处理原因{}：" + reason);
                }
            }
        } catch (ElasticsearchException  e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("不存在这个文档，请检查参数！");
            } else if (e.status() == RestStatus.CONFLICT) {
                System.out.println("版本冲突异常！");
            }
            System.out.println("更新失败！");
        }
    }
    
    /**
     * 批量增加文档
     * @author  GuangWei
     * @param params
     * @return  void
     * @exception  
     * @date       2019/5/19 15:08
     */
    public void bulkAdd(List<Map<String, String>> params) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        params.forEach(dataMap -> {
            String indexName = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String id = dataMap.get("id");
            String jsonString = dataMap.get("json");
            if (StrUtil.isNotBlank(id) && isJSONValid(jsonString)) {
                IndexRequest request = new IndexRequest(indexName).id(id).source(jsonString, XContentType.JSON);
                bulkRequest.add(request);
            }
        });
        if (bulkRequest.numberOfActions() == 0) {
            System.out.println("参数错误，批量增加操作失败！");
            return;
        }
        // 超时时间(2分钟)
        bulkRequest.timeout(TimeValue.timeValueMinutes(2L));
        // 刷新策略
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);

        BulkResponse responses = elasticClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        // 全部操作成功
        if (!responses.hasFailures()) {
            System.out.println("批量增加操作成功！");
        } else {
            for (BulkItemResponse bulkItemResponse : responses) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    System.out.println("index=" + failure.getIndex() + ",type=" + failure.getType() + ",id=" + failure.getId() + "的文档增加失败！");
                    System.out.println("增加失败详情{}：" + failure.getMessage());
                } else {
                    System.out.println("index=" + bulkItemResponse.getIndex() + ",type=" + bulkItemResponse.getType() + ",id=" + bulkItemResponse.getId() + "的文档增加成功！");
                }
            }
        }
    }
    
    /**
     * 批量更新文档
     * @author  GuangWei
     * @param params
     * @return  void
     * @exception  
     * @date       2019/5/19 15:33
     */
    public void bulkUpdate(List<Map<String, String>> params) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        params.forEach(dataMap -> {
            String indexName = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String id = dataMap.get("id");
            String jsonString = dataMap.get("json");
            if (StrUtil.isNotBlank(id) && isJSONValid(jsonString)) {
                UpdateRequest request = new UpdateRequest(indexName, id).doc(jsonString, XContentType.JSON);
                request.docAsUpsert(true);
                bulkRequest.add(request);
            }
        });
        if (bulkRequest.numberOfActions() == 0) {
            System.out.println("参数错误，批量更新操作失败！");
            return;
        }
        // 超时时间(2分钟)
        bulkRequest.timeout(TimeValue.timeValueMinutes(2L));
        // 刷新策略
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        BulkResponse responses = elasticClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (!responses.hasFailures()) {
            System.out.println("批量更新操作成功！");
        } else {
            for (BulkItemResponse bulkItemResponse : responses) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    System.out.println("index=" + failure.getIndex() + ",type=" + failure.getType() + ",id=" + failure.getId() + "的文档更新失败！");
                    System.out.println("更新失败详情{}：" + failure.getMessage());
                } else {
                    System.out.println("index=" + bulkItemResponse.getIndex() + ",type=" + bulkItemResponse.getType() + ",id=" + bulkItemResponse.getId() + "的文档更新成功！");
                }
            }
        }
    }
    
    /**
     * 批量删除文档
     * @author  GuangWei
     * @param params
     * @return  void
     * @exception
     * @date       2019/5/19 16:11
     */
    public void bulkDelete(List<Map<String, String>> params) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        params.forEach(dataMap -> {
            String indexName = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String id = dataMap.get("id");
            String jsonString = dataMap.get("json");
            if (StrUtil.isNotBlank(id) && isJSONValid(jsonString)) {
                DeleteRequest request = new DeleteRequest(indexName, id);
                bulkRequest.add(request);
            }
        });
        if (bulkRequest.numberOfActions() == 0) {
            System.out.println("操作失败，请检查参数！");
            return;
        }
        // 超时时间(2分钟)
        bulkRequest.timeout(TimeValue.timeValueMinutes(2L));
        // 刷新策略
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        BulkResponse responses = elasticClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (!responses.hasFailures()) {
            System.out.println("批量删除操作成功！");
        } else {
            for (BulkItemResponse bulkItemResponse : responses) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    System.out.println("index=" + failure.getIndex() + ",type=" + failure.getType() + ",id=" + failure.getId() + "的文档删除失败！");
                    System.out.println("删除失败详情{}：" + failure.getMessage());
                } else {
                    System.out.println("index=" + bulkItemResponse.getIndex() + ",type=" + bulkItemResponse.getType() + ",id=" + bulkItemResponse.getId() + "的文档删除成功！");
                }
            }
        }
    }

    /**
     * 批量查找文档
     * @author  GuangWei
     * @param params
     * @return  java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @exception  
     * @date       2019/5/19 16:03
     */
    public List<Map<String, Object>> multiGet(List<Map<String, String>> params) throws IOException {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        MultiGetRequest request = new MultiGetRequest();
        params.forEach(dataMap -> {
            String indexName = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String id = dataMap.get("id");
            String jsonString = dataMap.get("json");
            if (StrUtil.isNotBlank(id) ) {
                request.add(new MultiGetRequest.Item(indexName, id));
            }
        });
        request.realtime(false);
        request.refresh(true);
        MultiGetResponse responses = elasticClient.mget(request, RequestOptions.DEFAULT);
        List<Map<String, Object>> list = parseMGetResponse(responses);
        if (!CollectionUtil.isEmpty(list)) {
            resultList.addAll(list);
        }
        return resultList;
    }

    /**
     * 根据条件搜索日志内容(参数level和messageKey不能同时为空)
     * @author  GuangWei
     * @param level 日志级别，可以为空
     * @param messageKey 日志信息关键字，可以为空
     * @param startTime 日志起始时间，可以为空
     * @param endTime 日志结束时间，可以为空
     * @param size 返回记录数，可以为空，默认最大返回10条。该值必须小于10000，如果超过10000请使用  {@link #queryAllByConditions}
     * @return  java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @exception  
     * @date       2019/5/19 16:14
     */
    public List<Map<String, Object>> queryByConditions(String level, String messageKey, Long startTime, Long endTime, Integer size) throws IOException {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        if (StrUtil.isBlank(level) && StrUtil.isBlank(messageKey)) {
            System.out.println("参数level(日志级别)和messageKey(日志信息关键字)不能同时为空！");
            return resultList;
        }
        QueryBuilder query = generateQuery(level, messageKey, startTime, endTime);
        FieldSortBuilder order = SortBuilders.fieldSort(TIMESTAMP).order(SortOrder.ASC);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.timeout(TimeValue.timeValueMinutes(2L));
        searchBuilder.query(query);
        searchBuilder.sort(order);
        if (Objects.nonNull(size)) {
            searchBuilder.size(size);
        }

        SearchRequest request = new SearchRequest(INDEX);
        request.source(searchBuilder);
        SearchResponse response = elasticClient.search(request, RequestOptions.DEFAULT);
        int failedShards = response.getFailedShards();
        if (failedShards > 0) {
            System.out.println("部分分片副本处理失败！");
            for (ShardSearchFailure failure : response.getShardFailures()) {
                String reason = failure.reason();
                System.out.println("分片处理失败原因{}：" + reason);
            }
        }
        List<Map<String, Object>> list = parseSearchResponse(response);
        if (!CollectionUtil.isEmpty(list)) {
            resultList.addAll(list);
        }
        return resultList;
    }

    /**
     * 根据条件，搜索全部符合的记录(参数level和messageKey不能同时为空)
     * @author  GuangWei
     * @param level 日志级别，可以为空
     * @param messageKey 日志信息关键字，可以为空
     * @param startTime 日志起始时间，可以为空
     * @param endTime 日志结束时间，可以为空
     * @return  java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @exception
     * @date       2019/5/19 16:35
     */
    public List<Map<String, Object>> queryAllByConditions(String level, String messageKey, Long startTime, Long endTime) throws IOException {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        if (StrUtil.isBlank(level) && StrUtil.isBlank(messageKey)) {
            System.out.println("参数level(日志级别)和messageKey(日志信息关键字)不能同时为空！");
            return resultList;
        }

        QueryBuilder query = generateQuery(level, messageKey, startTime, endTime);
        FieldSortBuilder order = SortBuilders.fieldSort(TIMESTAMP).order(SortOrder.DESC);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.query(query).sort(order);
        searchBuilder.size(500);

        // 初始化 scroll 上下文
        SearchRequest request = new SearchRequest(INDEX).types(TYPE);
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        request.source(searchBuilder).scroll(scroll);
        SearchResponse response = elasticClient.search(request, RequestOptions.DEFAULT);
        String scrollId = response.getScrollId();
        SearchHit[] searchHits = response.getHits().getHits();
        // 把第一次scroll的数据添加到结果List中
        for (SearchHit searchHit : searchHits) {
            resultList.add(searchHit.getSourceAsMap());
        }
        // 通过传递scrollId循环取出所有相关文档
        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            response = elasticClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = response.getScrollId();
            searchHits = response.getHits().getHits();
            // 循环添加剩下的数据
            for (SearchHit searchHit : searchHits) {
                resultList.add(searchHit.getSourceAsMap());
            }
        }
        // 清理 scroll 上下文
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        elasticClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        return resultList;
    }

   /**
    * 根据条件做分页查询(参数level和messageKey不能同时为空)
    * @author  GuangWei
    * @param level 日志级别，可以为空
    * @param messageKey 日志信息关键字，可以为空
    * @param startTime 日志起始时间，可以为空
    * @param endTime 日志结束时间，可以为空
    * @param pageNum 当前页码，可以为空(默认设为1)
    * @param pageSize 页记录数，可以为空(默认设为10)
    * @return  Page<Map<String,Object>>
    * @exception
    * @date       2019/5/19 16:35
    */
    public PageBean<Map<String, Object>> queryPageByConditions(String level, String messageKey, Long startTime, Long endTime, Integer pageNum, Integer pageSize) throws IOException {
        if (StrUtil.isBlank(level) && StrUtil.isBlank(messageKey)) {
            System.out.println("参数level(日志级别)、messageKey(日志信息关键字)不能同时为空！");
            return null;
        }

        if (Objects.isNull(pageNum)) {
            pageNum = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = 10;
        }
        QueryBuilder query = generateQuery(level, messageKey, startTime, endTime);
        FieldSortBuilder order = SortBuilders.fieldSort(TIMESTAMP).order(SortOrder.DESC);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.timeout(TimeValue.timeValueMinutes(2L));
        searchBuilder.query(query);
        searchBuilder.sort(order);
        searchBuilder.from(pageNum - 1).size(pageSize);

        SearchRequest request = new SearchRequest(INDEX).types(TYPE);
        request.source(searchBuilder);
        SearchResponse response = elasticClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        int totalRecord = (int) hits.getTotalHits().value;
        List<Map<String, Object>> results = Lists.newArrayList();
        for (SearchHit hit : hits.getHits()) {
            results.add(hit.getSourceAsMap());
        }

        PageBean<Map<String, Object>> page = new PageBean<>();
        page.setCurrentPage(pageNum);
        page.setPageSize(pageSize);
        page.setTotalCount(totalRecord);
        page.setPageData(results);
        return page;
    }

    private XContentBuilder generateBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.startObject("properties");
        builder.startObject("message");
        // 为message字段，设置分词器为 ik_smart(最粗粒度)
        builder.field("type", "text");
        // 为message字段，设置分词器为 ik_smart(最粗粒度)
        builder.field("analyzer", "ik_smart");
        builder.endObject();
        builder.startObject(TIMESTAMP);
        // 设置 日志时间的格式为  毫秒数的long类型
        builder.field("type", "date");
        // 设置 日志时间的格式为  毫秒数的long类型
        builder.field("format", "epoch_millis");
        builder.endObject();
        builder.endObject();
        builder.endObject();
        return builder;
    }

    private List<Map<String, Object>> parseMGetResponse(MultiGetResponse response) {
        List<Map<String, Object>> list = Lists.newArrayList();
        MultiGetItemResponse[] responses = response.getResponses();
        for (MultiGetItemResponse item : responses) {
            GetResponse getResponse = item.getResponse();
            if (Objects.nonNull(getResponse)) {
                if (!getResponse.isExists()) {
                    System.out.println("index=" + getResponse.getIndex() + ",type=" + getResponse.getType() + ",id=" + getResponse.getId() + "的文档查找失败，请检查参数！");
                } else {
                    list.add(getResponse.getSourceAsMap());
                }
            } else {
                MultiGetResponse.Failure failure = item.getFailure();
                ElasticsearchException e = (ElasticsearchException) failure.getFailure();
                if (e.status() == RestStatus.NOT_FOUND) {
                    System.out.println("index=" + getResponse.getIndex() + ",type=" + getResponse.getType() + ",id=" + getResponse.getId() +  "的文档不存在！");
                } else if (e.status() == RestStatus.CONFLICT) {
                    System.out.println("index=" + getResponse.getIndex() + ",type=" + getResponse.getType() + ",id=" + getResponse.getId() +  "的文档版本冲突！");
                }
            }
        }
        return list;
    }

    public QueryBuilder generateQuery(String level, String messageKey, Long startTime, Long endTime) {
        // term query(检索level)
        TermQueryBuilder levelQuery = null;
        if (StrUtil.isNotBlank(level)) {
            levelQuery = QueryBuilders.termQuery("level", level.toLowerCase());
        }
        // match query(检索message)
        MatchQueryBuilder messageQuery = null;
        if (StrUtil.isNotBlank(messageKey)) {
            messageQuery = QueryBuilders.matchQuery("message", messageKey);
        }
        // range query(检索timestamp)
        RangeQueryBuilder timeQuery = QueryBuilders.rangeQuery(TIMESTAMP);
        timeQuery.format("epoch_millis");
        if (Objects.isNull(startTime)) {
            if (Objects.isNull(endTime)) {
                timeQuery = null;
            } else {
                timeQuery.lte(endTime);
            }
        } else {
            if (Objects.isNull(endTime)) {
                timeQuery.gte(startTime);
            } else {
                timeQuery.gte(startTime).lte(endTime);
            }
        }
        // 将上述三个query组合
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (Objects.nonNull(levelQuery)) {
            boolQuery.must(levelQuery);
        }
        if (Objects.nonNull(messageQuery)) {
            boolQuery.must(messageQuery);
        }
        if (Objects.nonNull(timeQuery)) {
            boolQuery.must(timeQuery);
        }
        return boolQuery;
    }

    private List<Map<String, Object>> parseSearchResponse(SearchResponse response) {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            resultList.add(hit.getSourceAsMap());
        }
        return resultList;
    }

    private boolean isJSONValid(String jsonString) {
        try {
            JSONObject.parseObject(jsonString);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(jsonString);
            } catch (JSONException exp) {
                return false;
            }
            return true;
        }
        return true;
    }
}
