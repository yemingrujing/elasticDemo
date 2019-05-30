package com.test.elasticsearch.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @ProjectName: elasticsearch
 * @Package: PACKAGE_NAME
 * @ClassName: com.test.elasticsearch.repository.base.BaseJPA
 * @Author: guang
 * @Description: 核心JPA
 * @Date: 2019/5/30 11:33
 * @Version: 1.0
 */
@NoRepositoryBean
public interface BaseJPA<T> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {
}
