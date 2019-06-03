package com.test.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_order")
public class OrderEntity  implements Serializable {

	/**
	 * 主键
	 */
  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 订单编号
	 */
	@Column(name = "order_code")
	private String orderCode;

	/**
	 * 商品总价
	 */
	@Column(name = "total_fee")
	private java.math.BigDecimal totalFee;

	/**
	 * 实付金额
	 */
	private java.math.BigDecimal payment;

	/**
	 * 支付类型(0支付宝/1微信/2银联/3线下支付)
	 */
	@Column(name = "pay_type")
	private Short payType;

	/**
	 * 付款状态(0未付款/1正在付款/2已付款/3退款/4支付超时)
	 */
	@Column(name = "pay_status")
	private Short payStatus;

	/**
	 * 订单类型(0普通/1活动/2集采/3品牌/4会员)
	 */
	@Column(name = "order_type")
	private Short orderType;

	/**
	 * 订单状态(0待付款/1待发货/2已发货/3完成/4订单取消/5超时/6退货/7关闭/8退款中/9退款成功/10退款拒绝)
	 */
	@Column(name = "order_status")
	private Short orderStatus;

	/**
	 * 付款时间
	 */
	@Column(name = "payment_time")
	private java.util.Date paymentTime;

	/**
	 * 发货时间
	 */
	@Column(name = "consign_time")
	private java.util.Date consignTime;

	/**
	 * 订单完成时间
	 */
	@Column(name = "end_time")
	private java.util.Date endTime;

	/**
	 * 订单超时时间
	 */
	@Column(name = "over_time")
	private java.util.Date overTime;

	/**
	 * 订单关闭时间
	 */
	@Column(name = "close_time")
	private java.util.Date closeTime;

	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 收货地址
	 */
	private Integer address;

	/**
	 * 活动ID
	 */
	@Column(name = "promotions_id")
	private Integer promotionsId;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private java.util.Date createTime;

	/**
	 * 物流公司编号
	 */
	@Column(name = "logistics_num")
	private String logisticsNum;

	/**
	 * 快递单号
	 */
	@Column(name = "courier_num")
	private String courierNum;

	/**
	 * 子账号ID
	 */
	@Column(name = "sub_user_id")
	private Integer subUserId;

	/**
	 * 质押金额
	 */
	@Column(name = "cash_pledge")
	private java.math.BigDecimal cashPledge;

	/**
	 * 发票金额
	 */
	@Column(name = "invoice_amount")
	private java.math.BigDecimal invoiceAmount;

}
