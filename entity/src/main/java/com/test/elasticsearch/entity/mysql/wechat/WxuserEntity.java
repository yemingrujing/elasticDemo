package com.test.elasticsearch.entity.mysql.wechat;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "t_wxuser")
public class WxuserEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "open_id")
	private String openId;

	@Column(name = "app_id")
	private String appId;

	@Column(name = "arc_id")
	private String arcId;

	@Column(name = "avatar_url")
	private String avatarUrl;

	private String city;

	private String language;

	@Column(name = "nick_name")
	private String nickName;

	private String province;

	@Column(name = "tel_number")
	private String telNumber;

	@Column(name = "u_name")
	private String uName;

	private String time;

	@Column(name = "joiner_name")
	private String joinerName;

	@Column(name = "joiner_tel")
	private String joinerTel;

	@Column(name = "joiner_remark")
	private String joinerRemark;

}
