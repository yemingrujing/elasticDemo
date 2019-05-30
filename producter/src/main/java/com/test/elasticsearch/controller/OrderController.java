package com.test.elasticsearch.controller;

import com.querydsl.core.QueryResults;
import com.test.elasticsearch.dto.OrderDTO;
import com.test.elasticsearch.param.OrderParam;
import com.test.elasticsearch.service.OrderService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/order/query")
    public Result query(OrderParam param) {
        QueryResults<OrderDTO> list = orderService.query(param);
        return ResultUtil.success(list);
    }
}
