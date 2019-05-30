package com.test.elasticsearch.service.impl;

import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.elasticsearch.dto.OrderDTO;
import com.test.elasticsearch.entity.QOrderEntity;
import com.test.elasticsearch.entity.QUserEntity;
import com.test.elasticsearch.param.OrderParam;
import com.test.elasticsearch.repository.OrderRepository;
import com.test.elasticsearch.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    // JPA查询工厂
    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<OrderDTO> query(OrderParam param) {
        QOrderEntity orderEntity = QOrderEntity.orderEntity;
        QUserEntity userEntity = QUserEntity.userEntity;
        JPAQuery<OrderDTO> query = queryFactory.select(
                Projections.bean(
                        OrderDTO.class,
                        orderEntity.id,
                        userEntity.phone,
                        userEntity.nickName,
                        orderEntity.orderCode,
                        orderEntity.totalFee
                )
        )
                .from(orderEntity)
                .leftJoin(userEntity).on(orderEntity.userId.eq(userEntity.id));
        if (StrUtil.isNotBlank(param.getOrderCode())) {
            query.where(orderEntity.orderCode.like("%" + param.getOrderCode() + "%"));
        }
        if (StrUtil.isNotBlank(param.getPhone())) {
            query.where(userEntity.phone.like("%" + param.getPhone() + "%"));
        }
        if (param.getOrderType() != null) {
            query.where(orderEntity.orderType.eq(param.getOrderType()));
        }
        if (param.getUserId() != null) {
            query.where(userEntity.id.eq(param.getUserId()));
        }
        return query.fetch();
    }
}
