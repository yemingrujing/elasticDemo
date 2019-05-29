package com.test.elasticsearch.service.impl;

import com.test.elasticsearch.dto.OrderDTO;
import com.test.elasticsearch.entity.OrderEntity;
import com.test.elasticsearch.param.OrderParam;
import com.test.elasticsearch.repository.OrderRepository;
import com.test.elasticsearch.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        
        orderRepository.findAll(new Specification<OrderEntity>() {
            @Override
            public Predicate toPredicate(Root<OrderEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        });
        return null;
    }
}
