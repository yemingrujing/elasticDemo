package com.test.elasticsearch.repository.mongodb;

import com.test.elasticsearch.entity.mongodb.NovelDb;
import com.test.elasticsearch.repository.mongodb.base.BaseJPA;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.repository.mongodb
 * @ClassName: NovelMBRepository
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/8/2 14:47
 * @Version: 1.0
 */
@Repository
public interface NovelMBRepository extends BaseJPA<NovelDb> {
}
