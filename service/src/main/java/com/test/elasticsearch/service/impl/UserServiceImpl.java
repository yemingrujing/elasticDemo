package com.test.elasticsearch.service.impl;

import com.test.elasticsearch.repository.UserRepository;
import com.test.elasticsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
