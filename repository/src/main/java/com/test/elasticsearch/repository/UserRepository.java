package com.test.elasticsearch.repository;

import com.test.elasticsearch.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.repository
 * @ClassName: UserRepository
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/5/28 19:16
 * @Version: 1.0
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
