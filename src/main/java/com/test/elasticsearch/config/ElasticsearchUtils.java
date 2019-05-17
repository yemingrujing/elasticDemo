package com.test.elasticsearch.config;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
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

    /**
     * 类型
     */
    private static final String TYPE = "doc";

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
        AcknowledgedResponse response = elasticClient.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
        if (response.isAcknowledged()) {
            System.out.println("索引删除成功！索引名称为{}: " + indexName);
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
        } catch (IOException e) {
            System.out.println("文档新增失败！");
        }
    }

    /**
     * 查找文档
     * @param indexName
     * @param id
     * @return
     */
    public Map<String, Object> getDocument(String indexName, String id) {
        Map<String, Object> resultMap = new HashMap<>();
        GetRequest request = new GetRequest(indexName).id(id);
        // 实时(否)
        request.realtime(false);
        // 检索之前执行刷新(是)
        request.refresh(true);

        GetResponse response = null;
        try {
            response = elasticClient.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
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
    public void deleteDocument(String indexName, String id) {
        DeleteRequest request = new DeleteRequest(indexName).id(id);
        DeleteResponse response = null;
        try {
            response = elasticClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
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
    public void updateDocByScript(String indexName, String id, String script) {
        Script inline = new Script(script);
        UpdateRequest request = new UpdateRequest(indexName, id).script(inline);
        try {
            UpdateResponse response = elasticClient.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            System.out.println("更新失败！");
        }
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

    public static void main(String[] args) {

    }
}
