package com.test.elasticsearch.service;

import com.querydsl.core.QueryResults;
import com.test.elasticsearch.dto.OrderDTO;
import com.test.elasticsearch.param.OrderParam;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service
 * @ClassName: OrderService
 * @Author: guang
 * @Description: 订单
 * @Date: 2019/5/28 19:18
 * @Version: 1.0
 */
public interface OrderService {

    QueryResults<OrderDTO> query(OrderParam param);
}
