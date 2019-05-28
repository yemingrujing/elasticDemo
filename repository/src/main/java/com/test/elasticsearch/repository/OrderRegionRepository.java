package com.test.elasticsearch.repository;

import com.test.elasticsearch.entity.OrderRegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.repository
 * @ClassName: OrderRegionRepository
 * @Author: guang
 * @Description:
 * @Date: 2019/5/20 10:23
 * @Version: 1.0
 */
@Repository
public interface  OrderRegionRepository extends JpaRepository<OrderRegionEntity, Integer> {

}
