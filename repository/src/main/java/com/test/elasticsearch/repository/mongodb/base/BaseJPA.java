package com.test.elasticsearch.repository.mongodb.base;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author guang
 * @title: BaseJPA
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/6/221:06
 */
@NoRepositoryBean
public interface BaseJPA<T> extends MongoRepository<T, Integer>, QuerydslPredicateExecutor<T> {
}
