package com.test.elasticsearch.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.elasticsearch.dto.OrderDTO;
import com.test.elasticsearch.entity.OrderEntity;
import com.test.elasticsearch.param.OrderParam;
import com.test.elasticsearch.repository.OrderRepository;
import com.test.elasticsearch.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.impl
 * @ClassName: OrderServiceImpl
 * @Author: guang
 * @Description: 订单service
 * @Date: 2019/5/28 19:19
 * @Version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<OrderDTO> query(OrderParam param) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        orderRepository.findAll();
        return null;
    }
}
