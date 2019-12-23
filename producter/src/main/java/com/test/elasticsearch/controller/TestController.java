package com.test.elasticsearch.controller;

import com.google.common.collect.Maps;
import com.test.elasticsearch.config.rabbitmq.RabbitSender;
import com.test.elasticsearch.config.redis.RedisService;
import com.test.elasticsearch.domain.Order;
import com.test.elasticsearch.utils.FileUtil;
import com.test.elasticsearch.utils.captcha.RandonImgCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

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

    @Autowired
    private RedisService redisService;

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
    @GetMapping("/test/captcha/generate")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");
        // 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);

        try {
            // 生成随机验证码
            int charSize = 6;
            Map<String, Object> map = RandonImgCodeUtil.generateVerifyCode(charSize, new Random().nextInt(2));

            if (Objects.nonNull(map.get("result"))) {
                String key = "captcha:" + request.getSession().getId() + "_" + UUID.randomUUID().toString().replace("-", "");
                redisService.set(key, map.get("result"), 90L);
            }

            // 生成图片规格w宽 h高
            int w = 100, h = 40;
            int type = new Random().nextInt(7);
            RandonImgCodeUtil.generate(response.getOutputStream(), w, h, (String) map.get("key"), type);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
