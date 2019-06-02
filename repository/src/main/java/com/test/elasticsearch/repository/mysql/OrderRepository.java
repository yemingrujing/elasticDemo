package com.test.elasticsearch.repository.mysql;

import com.test.elasticsearch.entity.OrderEntity;
import com.test.elasticsearch.repository.mysql.base.BaseJPA;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.repository
 * @ClassName: OrderRepository
 * @Author: guang
 * @Description:
 * @Date: 2019/5/28 19:14
 * @Version: 1.0
 */
@Repository
public interface OrderRepository extends BaseJPA<OrderEntity> {

}
