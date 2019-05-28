package com.test.elasticsearch.dto;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.dto
 * @ClassName: OrderDTO
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/5/28 19:52
 * @Version: 1.0
 */
@Data
public class OrderDTO {

    /**
     * 订单ID
     */
    private Integer id;

    /**
     * 用户账号
     */
    private String phone;

    /**
     * 用户昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 用户订单号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 总费用
     */
    @Column(name = "total_fee")
    private BigDecimal totalFee;
}
