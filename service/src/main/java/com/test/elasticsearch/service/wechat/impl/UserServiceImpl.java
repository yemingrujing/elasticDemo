package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.util.StrUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import com.test.elasticsearch.entity.mysql.wechat.UserEntity;
import com.test.elasticsearch.repository.mysql.wechat.UserRepository;
import com.test.elasticsearch.service.wechat.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.impl
 * @ClassName: UserServiceImpl
 * @Author: guang
 * @Description: 用户
 * @Date: 2019/5/28 19:19
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> findAll(String phone) {
        PredicateBuilder<UserEntity> builder =  Specifications.and();
        if (StrUtil.isNotBlank(phone)) {
            builder.eq("phone", phone);
        }
        return userRepository.findAll(builder.build());
    }
}
