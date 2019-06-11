package com.test.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.QueryResults;
import com.test.elasticsearch.config.rabbitmq.RabbitSender;
import com.test.elasticsearch.config.rabbitmq.RabbitmqConf;
import com.test.elasticsearch.dto.OrderDTO;
import com.test.elasticsearch.enums.DirectEnum;
import com.test.elasticsearch.param.OrderParam;
import com.test.elasticsearch.service.OrderService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller
 * @ClassName: OrderController
 * @Author: guang
 * @Description: 订单controller
 * @Date: 2019/5/30 12:35
 * @Version: 1.0
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitSender rabbitSender;

    @GetMapping("/order/query")
    public Result query(OrderParam param) {
        QueryResults<OrderDTO> list = orderService.query(param);
        return ResultUtil.success(list);
    }

    @GetMapping("/mq/order/query")
    public Result queryByMq(OrderParam param) {
        rabbitSender.sendMessage(RabbitmqConf.DIRECT_EXCHANGE, DirectEnum.DirectKey.name(), JSON.toJSONString(param));
        return ResultUtil.success();
    }

    @PostMapping("/order/mongoDB/save")
    public Result saveToMongoDB(OrderParam param) {
        orderService.saveToMongoDB(param);
        return ResultUtil.success();
    }

    @PostMapping("/order/mongoDB/del")
    public Result delToMongoDB(OrderParam param) {
        orderService.delToMongoDB(param);
        return ResultUtil.success();
    }

    @GetMapping("/order/mongoDB/select")
    public Result selectToMongoDB(OrderParam param) {
        return ResultUtil.success(orderService.selectToMongoDB(param));
    }
}
