package com.test.elasticsearch.repository;

import com.test.elasticsearch.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface OrderRepository extends JpaRepository<OrderEntity, Integer>, JpaSpecificationExecutor<OrderEntity> {

}
