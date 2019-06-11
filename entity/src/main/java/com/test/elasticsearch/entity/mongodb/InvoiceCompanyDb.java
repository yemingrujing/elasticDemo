package com.test.elasticsearch.entity.mongodb;

import com.test.elasticsearch.anno.AutoValue;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@Document(collection = "t_invoice_company")
public class InvoiceCompanyDb implements Serializable {

	/**
	 * 主键
	 */
  	@Id
	@AutoValue
	@Field("id")
	private Long id;

	/**
	 * 6位开票代码
	 */
	@Field("code")
	private String code;

	/**
	 * 公司名称
	 */
	@Field("kp_name")
	private String kpName;

	/**
	 * 纳税人识别号
	 */
	@Field("kp_code")
	private String kpCode;

	/**
	 * 银行名称
	 */
	@Field("bank_name")
	private String bankName;

	/**
	 * 银行账号
	 */
	@Field("bank_account")
	private String bankAccount;

	/**
	 * 注册电话
	 */
	@Field("telephone")
	private String telephone;

	/**
	 * 单位注册地址
	 */
	@Field("register_address")
	private String registerAddress;

}
