package com.test.elasticsearch.entity.mysql.wechat;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_wxuser")
public class WxuserEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String openid;

	@Column(name = "AppID")
	private String appid;

	@Column(name = "arcID")
	private String arcid;

	@Column(name = "avatarUrl")
	private String avatarurl;

	private String city;

	private String language;

	@Column(name = "nickName")
	private String nickname;

	private String province;

	@Column(name = "telNumber")
	private String telnumber;

	@Column(name = "uName")
	private String uname;

	private String time;

	@Column(name = "joinerName")
	private String joinername;

	@Column(name = "joinerTel")
	private String joinertel;

	@Column(name = "joinerRemark")
	private String joinerremark;

}
