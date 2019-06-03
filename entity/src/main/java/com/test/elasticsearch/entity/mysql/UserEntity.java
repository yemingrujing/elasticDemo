package com.test.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_user")
public class UserEntity  implements Serializable {

	/**
	 * 主键
	 */
  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 用户密码
	 */
	@Column(name = "pass_word")
	private String passWord;

	/**
	 * 性别（男-0,1-女）
	 */
	private Short sex;

	/**
	 * 头像地址
	 */
	@Column(name = "image_url")
	private String imageUrl;

	/**
	 * 用户昵称
	 */
	@Column(name = "nick_name")
	private String nickName;

	/**
	 * 用户名称
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 用户生日
	 */
	private java.util.Date birthday;

	/**
	 * QQ
	 */
	private String qq;

	/**
	 * 微信
	 */
	@Column(name = "wei_xin")
	private String weiXin;

	/**
	 * 允许设备最大接入数量
	 */
	@Column(name = "max_login_number")
	private Integer maxLoginNumber;

	/**
	 * 用户登录身份令牌
	 */
	@Column(name = "to_ken")
	private String toKen;

	/**
	 * 是否年缴费( 0 - 否，1 - 是) 第一次缴费后必须按年缴费
	 */
	@Column(name = "is_year_payment")
	private Short isYearPayment;

	/**
	 * 登录设备token
	 */
	@Column(name = "device_token")
	private String deviceToken;

	/**
	 * 登录设备类型
	 */
	@Column(name = "device_type")
	private String deviceType;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private java.util.Date createTime;

	/**
	 * 用户是否被锁定(0:未锁定，1:锁定)
	 */
	private Short locked;

	/**
	 * 注册渠道(0：APP,1：PC,2：旁听)
	 */
	@Column(name = "register_channel")
	private Short registerChannel;

	/**
	 * 最后登录时间
	 */
	@Column(name = "login_time")
	private java.util.Date loginTime;

}
