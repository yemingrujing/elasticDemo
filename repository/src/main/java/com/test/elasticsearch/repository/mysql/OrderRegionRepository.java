package com.test.elasticsearch.repository.mysql;

import com.test.elasticsearch.entity.mysql.OrderRegionEntity;
import com.test.elasticsearch.repository.mysql.base.BaseJPA;
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
public interface  OrderRegionRepository extends BaseJPA<OrderRegionEntity> {

}
