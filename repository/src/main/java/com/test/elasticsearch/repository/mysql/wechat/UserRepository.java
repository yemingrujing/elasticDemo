package com.test.elasticsearch.repository.mysql.wechat;

import com.test.elasticsearch.entity.mysql.wechat.UserEntity;
import com.test.elasticsearch.repository.mysql.base.BaseJPA;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.repository
 * @ClassName: UserRepository
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/5/28 19:16
 * @Version: 1.0
 */
@Repository
public interface UserRepository extends BaseJPA<UserEntity> {

}
