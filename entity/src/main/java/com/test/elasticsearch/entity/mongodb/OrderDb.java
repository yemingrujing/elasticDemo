package com.test.elasticsearch.entity.mongodb;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Document(collection = "t_order")
@CompoundIndexes(
		@CompoundIndex(name = "orderCode_idx", def = "{'order_code': 1, 'user_id': 1}")
)
public class OrderDb implements Serializable {

	/**
	 * 主键
	 */
  	@Id
	@Indexed(unique = true)
	@Field("id")
	private Integer id;

	/**
	 * 订单编号
	 */
	@Field("order_code")
	private String orderCode;

	/**
	 * 商品总价
	 */
	@Field("total_fee")
	private java.math.BigDecimal totalFee;

	/**
	 * 实付金额
	 */
	@Field("payment")
	private java.math.BigDecimal payment;

	/**
	 * 支付类型(0支付宝/1微信/2银联/3线下支付)
	 */
	@Field("pay_type")
	private Short payType;

	/**
	 * 付款状态(0未付款/1正在付款/2已付款/3退款/4支付超时)
	 */
	@Field("pay_status")
	private Short payStatus;

	/**
	 * 订单类型(0普通/1活动/2集采/3品牌/4会员)
	 */
	@Field("order_type")
	private Short orderType;

	/**
	 * 订单状态(0待付款/1待发货/2已发货/3完成/4订单取消/5超时/6退货/7关闭/8退款中/9退款成功/10退款拒绝)
	 */
	@Field("order_status")
	private Short orderStatus;

	/**
	 * 付款时间
	 */
	@Field("payment_time")
	private java.util.Date paymentTime;

	/**
	 * 发货时间
	 */
	@Field("consign_time")
	private java.util.Date consignTime;

	/**
	 * 订单完成时间
	 */
	@Field("end_time")
	private java.util.Date endTime;

	/**
	 * 订单超时时间
	 */
	@Field("over_time")
	private java.util.Date overTime;

	/**
	 * 订单关闭时间
	 */
	@Field("close_time")
	private java.util.Date closeTime;

	/**
	 * 用户ID
	 */
	@Field("user_id")
	private Integer userId;

	/**
	 * 手机号码
	 */
	@Field("phone")
	private String phone;

	/**
	 * 收货地址
	 */
	@Field("address")
	private Integer address;

	/**
	 * 活动ID
	 */
	@Field("promotions_id")
	private Integer promotionsId;

	/**
	 * 创建时间
	 */
	@Field("create_time")
	private java.util.Date createTime;

	/**
	 * 物流公司编号
	 */
	@Field("logistics_num")
	private String logisticsNum;

	/**
	 * 快递单号
	 */
	@Field("courier_num")
	private String courierNum;

	/**
	 * 子账号ID
	 */
	@Field("sub_user_id")
	private Integer subUserId;

	/**
	 * 质押金额
	 */
	@Field("cash_pledge")
	private java.math.BigDecimal cashPledge;

	/**
	 * 发票金额
	 */
	@Field("invoice_amount")
	private java.math.BigDecimal invoiceAmount;

}
