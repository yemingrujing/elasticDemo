package com.test.elasticsearch.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.param
 * @ClassName: OrderParam
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/5/28 20:04
 * @Version: 1.0
 */
@Setter
@Getter
public class OrderParam {

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单类型
     */
    private Short orderType;
}
