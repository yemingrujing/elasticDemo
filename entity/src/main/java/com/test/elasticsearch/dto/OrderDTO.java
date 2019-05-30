package com.test.elasticsearch.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class OrderDTO implements Serializable {

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
    private String nickName;

    /**
     * 用户订单号
     */
    private String orderCode;

    /**
     * 总费用
     */
    private BigDecimal totalFee;

    /**
     * 创建时间
     */
    private Date createTime;
}
