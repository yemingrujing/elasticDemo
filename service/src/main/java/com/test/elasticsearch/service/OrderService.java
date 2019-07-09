package com.test.elasticsearch.service;

import com.querydsl.core.QueryResults;
import com.test.elasticsearch.dto.OrderDTO;
import com.test.elasticsearch.entity.mongodb.OrderDb;
import com.test.elasticsearch.param.OrderParam;

import java.util.List;

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

    /**
     * 查询订单列表
     * @author  GuangWei
     * @param param
     * @return  com.querydsl.core.QueryResults<com.test.elasticsearch.dto.OrderDTO>
     * @exception
     * @date       2019/6/2
     */
    QueryResults<OrderDTO> query(OrderParam param);

    /**
     * 根据订单号保存数据到mongoDB
     * @author  GuangWei
     * @param param
     * @return  void
     * @exception
     * @date       2019/6/2 21:30
     */
    void saveToMongoDB(OrderParam param);

    /**
     * 根据条件删除数据
     * @author  GuangWei
     * @param param
     * @return  void
     * @exception
     * @date       2019/6/2 21:30
     */
    void delToMongoDB(OrderParam param);

    /**
     * 根据条件删除数据
     * @author  GuangWei
     * @param param
     * @return  void
     * @exception
     * @date       2019/6/2 21:30
     */
    List<OrderDb> selectToMongoDB(OrderParam param);
}
