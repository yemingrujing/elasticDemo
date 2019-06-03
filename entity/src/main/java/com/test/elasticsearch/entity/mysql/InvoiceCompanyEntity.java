package com.test.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_invoice_company")
public class InvoiceCompanyEntity implements Serializable {

	/**
	 * 主键
	 */
  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 6位开票代码
	 */
	private String code;

	/**
	 * 公司名称
	 */
	@Column(name = "kp_name")
	private String kpName;

	/**
	 * 纳税人识别号
	 */
	@Column(name = "kp_code")
	private String kpCode;

	/**
	 * 银行名称
	 */
	@Column(name = "bank_name")
	private String bankName;

	/**
	 * 银行账号
	 */
	@Column(name = "bank_account")
	private String bankAccount;

	/**
	 * 注册电话
	 */
	private String telephone;

	/**
	 * 单位注册地址
	 */
	@Column(name = "register_address")
	private String registerAddress;

}
