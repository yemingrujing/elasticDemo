package com.test.elasticsearch.repository.mongodb;

import com.test.elasticsearch.entity.OrderEntity;
import com.test.elasticsearch.repository.mongodb.base.BaseJPA;
import org.springframework.stereotype.Repository;

/**
 * @author guang
 * @title: OrderMBRepository
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/6/221:17
 */
@Repository
public interface OrderMBRepository extends BaseJPA<OrderEntity> {

}
