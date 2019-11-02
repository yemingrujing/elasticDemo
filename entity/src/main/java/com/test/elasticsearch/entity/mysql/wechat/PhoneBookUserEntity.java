package com.test.elasticsearch.entity.mysql.wechat;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_phone_book_user")
public class PhoneBookUserEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "open_id")
	private String openId;

	private String name;

	private String tel;

	private String address;

	private String remark;

	@Column(name = "g_id")
	private String gId;

}
