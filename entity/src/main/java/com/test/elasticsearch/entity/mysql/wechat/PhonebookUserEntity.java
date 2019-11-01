package com.test.elasticsearch.entity.mysql.wechat;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_phoneBook_user")
public class PhonebookUserEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String openid;

	private String name;

	private String tel;

	private String address;

	private String remark;

	@Column(name = "gId")
	private String gid;

}
