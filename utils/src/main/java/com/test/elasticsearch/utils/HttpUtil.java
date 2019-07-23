package com.test.elasticsearch.utils;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.utils
 * @ClassName: HttpUtil
 * @Author: guang
 * @Description: Http请求
 * @Date: 2019/7/23 10:15
 * @Version: 1.0
 */
@Slf4j
public class HttpUtil {

    public static final int DEF_CONN_TIMEOUT = 30000;

    public static final int DEF_READ_TIMEOUT = 30000;

    public static final int DEF_CONN_REQ_TIMEOUT = 10000;

    public static <T, E extends List> T urlPost(String url, E reqData, Map<String, String> headers, Class<T> returnClass) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(DEF_CONN_TIMEOUT)
                .setSocketTimeout(DEF_READ_TIMEOUT).setConnectionRequestTimeout(DEF_CONN_REQ_TIMEOUT).build();
        HttpPost httpPost = new HttpPost(url);
        for (String key : headers.keySet()) {
            httpPost.setHeader(key, headers.get(key));
        }
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(reqData);
            httpPost.setEntity(urlEncodedFormEntity);
            httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
            return JSON.parseObject(URLDecoder.decode(EntityUtils.toString(response.getEntity(), CharsetUtil.UTF_8), CharsetUtil.UTF_8), returnClass);
        } catch (Exception e) {
            log.error("HttpUtil Request Failure：{}" + ExceptionUtils.getStackTrace(e),  url);
            throw new RuntimeException("Http请求发送失败");
        }
    }
}
