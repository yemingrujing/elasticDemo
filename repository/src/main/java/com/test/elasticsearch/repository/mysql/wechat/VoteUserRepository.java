package com.test.elasticsearch.repository.mysql.wechat;

import com.test.elasticsearch.entity.mysql.wechat.VoteUserEntity;
import com.test.elasticsearch.repository.mysql.base.BaseJPA;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.repository.mysql
 * @ClassName: VoteUserRepository
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/1 17:57
 * @Version: 1.0
 */
@Repository
public interface VoteUserRepository extends BaseJPA<VoteUserEntity> {
}
