package com.test.elasticsearch.service;

import com.test.elasticsearch.entity.mysql.UserEntity;

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
