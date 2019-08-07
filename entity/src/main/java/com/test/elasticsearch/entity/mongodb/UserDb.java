package com.test.elasticsearch.entity.mongodb;

import com.test.elasticsearch.anno.AutoValue;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@Document(collection = "t_user")
// 数字参数指定索引的方向，1为正序，-1为倒序
@CompoundIndexes(
		@CompoundIndex(name = "user_phone_name_index", def = "{'nickName': 1, 'creatTime': -1}")
)
public class UserDb implements Serializable {

	/**
	 * 主键
	 */
  	@Id
	@AutoValue
	@Field("id")
	@Builder.Default
	private Long id = 0L;

	/**
	 * 手机号
	 */
	@Indexed(unique = true)
	@Field("phone")
	private String phone;

	/**
	 * 电子邮件
	 */
	@Field("email")
	private String email;

	/**
	 * 用户密码
	 */
	@Field("pass_word")
	private String passWord;

	/**
	 * 性别（男-0,1-女）
	 */
	@Field("sex")
	private Short sex;

	/**
	 * 头像地址
	 */
	@Field("image_url")
	private String imageUrl;

	/**
	 * 用户昵称
	 */
	@Field("nick_name")
	private String nickName;

	/**
	 * 用户名称
	 */
	@Field("user_name")
	private String userName;

	/**
	 * 用户生日
	 */
	@Field("birthday")
	private java.util.Date birthday;

	/**
	 * QQ
	 */
	@Field("qq")
	private String qq;

	/**
	 * 微信
	 */
	@Field("wei_xin")
	private String weiXin;

	/**
	 * 允许设备最大接入数量
	 */
	@Field("max_login_number")
	private Long maxLoginNumber;

	/**
	 * 用户登录身份令牌
	 */
	@Field("to_ken")
	private String toKen;

	/**
	 * 是否年缴费( 0 - 否，1 - 是) 第一次缴费后必须按年缴费
	 */
	@Field("is_year_payment")
	private Short isYearPayment;

	/**
	 * 登录设备token
	 */
	@Field("device_token")
	private String deviceToken;

	/**
	 * 登录设备类型
	 */
	@Field("device_type")
	private String deviceType;

	/**
	 * 创建时间
	 */
	@Field("create_time")
	private java.util.Date createTime;

	/**
	 * 用户是否被锁定(0:未锁定，1:锁定)
	 */
	@Field("locked")
	private Short locked;

	/**
	 * 注册渠道(0：APP,1：PC,2：旁听)
	 */
	@Field("register_channel")
	private Short registerChannel;

	/**
	 * 最后登录时间
	 */
	@Field("login_time")
	private java.util.Date loginTime;

}
