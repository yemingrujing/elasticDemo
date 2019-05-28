package com.test.elasticsearch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author guang
 * @title: Order
 * @projectName elasticDemo
 * @description: 自定义消息
 * @date 2019/5/2622:41
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private String id;
    private String name;
}
