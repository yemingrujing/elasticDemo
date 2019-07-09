package com.test.elasticsearch.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.elasticsearch.dto.OrderDTO;
import com.test.elasticsearch.entity.mongodb.OrderDb;
import com.test.elasticsearch.entity.mysql.OrderEntity;
import com.test.elasticsearch.entity.mysql.QOrderEntity;
import com.test.elasticsearch.entity.mysql.QUserEntity;
import com.test.elasticsearch.param.OrderParam;
import com.test.elasticsearch.repository.mongodb.OrderMBRepository;
import com.test.elasticsearch.repository.mysql.OrderRepository;
import com.test.elasticsearch.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMBRepository orderMBRepository;

    @Autowired
    private EntityManager entityManager;

    // JPA查询工厂
    private JPAQueryFactory queryFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public QueryResults<OrderDTO> query(OrderParam param) {
        QOrderEntity orderEntity = QOrderEntity.orderEntity;
        QUserEntity userEntity = QUserEntity.userEntity;
        JPAQuery<OrderDTO> query = queryFactory.select(
                Projections.bean(
                        OrderDTO.class,
                        orderEntity.id,
                        userEntity.phone,
                        userEntity.nickName,
                        orderEntity.orderCode,
                        orderEntity.totalFee,
                        orderEntity.createTime
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
        return query.orderBy(orderEntity.createTime.desc()).offset(param.getOffset()).limit(param.getPageSize()).fetchResults();
    }

    @Override
    public void saveToMongoDB(OrderParam param) {
        PredicateBuilder<OrderEntity> builder = Specifications.and();
        if (StrUtil.isNotBlank(param.getOrderCode())) {
            builder.like("orderCode", "%" + param.getOrderCode() + "%");
        }
        if (param.getUserId() != null) {
            builder.eq("userId", param.getUserId());
        }
        if (param.getOrderType() != null) {
            builder.eq("orderType", param.getOrderType());
        }
        List<OrderEntity> orderEntityList = orderRepository.findAll(builder.build());
        if (CollectionUtil.isNotEmpty(orderEntityList)) {
            List<OrderDb> orderDbList = JSON.parseArray(JSON.toJSONString(orderEntityList), OrderDb.class);
            //orderMBRepository.insert(orderDbList);
            // 使用mongoTemplate插入数据
            mongoTemplate.insert(orderDbList, OrderDb.class);
        }
    }

    @Override
    public void delToMongoDB(OrderParam param) {
        Criteria criteria = new Criteria();
        if (StrUtil.isNotBlank(param.getOrderCode())) {
            criteria = criteria.and("orderCode").is(param.getOrderCode());
        }
        if (param.getUserId() != null) {
            criteria = criteria.and("userId").is(param.getUserId());
        }
        if (param.getOrderType() != null) {
            criteria = criteria.and("orderType").is(param.getOrderType());
        }
        Query query = new Query(criteria);
        mongoTemplate.remove(query, OrderDb.class);
    }

    @Override
    public List<OrderDb> selectToMongoDB(OrderParam param) {
        Criteria criteria = new Criteria();
        if (param.getId() != null) {
            criteria = criteria.and("id").is(param.getId());
        }
        if (StrUtil.isNotBlank(param.getOrderCode())) {
            criteria = criteria.and("orderCode").is(param.getOrderCode());
        }
        if (param.getUserId() != null) {
            criteria = criteria.and("userId").is(param.getUserId());
        }
        if (param.getOrderType() != null) {
            criteria = criteria.and("orderType").is(param.getOrderType());
        }
        Query query = new Query(criteria);
        return mongoTemplate.find(query, OrderDb.class);
    }
}
