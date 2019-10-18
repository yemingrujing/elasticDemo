package com.test.elasticsearch.domain;

import lombok.*;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 订单code
    */
    private String orderCode;

    /**
     * 用户ID
     */
    private Integer userId;
}
