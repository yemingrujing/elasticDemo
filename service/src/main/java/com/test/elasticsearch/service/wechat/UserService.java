package com.test.elasticsearch.service.wechat;

import com.test.elasticsearch.entity.mysql.wechat.UserEntity;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service
 * @ClassName: UserService
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/5/28 19:18
 * @Version: 1.0
 */
public interface UserService {

    List<UserEntity> findAll(String phone);
}
