package com.test.elasticsearch.controller;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.test.elasticsearch.config.rabbitmq.RabbitSender;
import com.test.elasticsearch.domain.Order;
import com.test.elasticsearch.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author guang
 * @title: TestController
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/5/2223:19
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private RabbitSender rabbitSender;

    @GetMapping("/test")
    public void test(){
        log.info("进入test方法");
        log.debug("进入test方法");
        log.warn("进入test方法");
        log.error("进入test方法");
    }

    /**
     * 测试MQ自定义消息
     */
    @GetMapping("/mq/send/order")
    public void testMqOrder(){
        Order order = new Order();
        order.setId(2936);
        order.setOrderCode("42019030816050432845");
        order.setUserId(745);
        rabbitSender.sendOrder(order);
    }

    @GetMapping("/test/word/export")
    public void exportWord(@RequestParam(required = false) String brandIntroduce,
                           @RequestParam(required = false) String mainPushIntroduce,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("brandName", "薇诗海雅");
        params.put("companyName", "仪菲（上海）品牌管理有限公司");
        params.put("intervalMin", 100);
        params.put("intervalMax", 200);
        params.put("brandIntroduce", brandIntroduce);
        params.put("mainPushIntroduce", mainPushIntroduce);
        FileUtil.exportWord("word/export.docx", "aaa.docx", params, request, response);
    }
}
