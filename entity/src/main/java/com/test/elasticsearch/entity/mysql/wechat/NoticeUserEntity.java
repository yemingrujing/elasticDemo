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
@Table(name = "t_notice_user")
public class NoticeUserEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "notice_id")
	private String noticeId;

	@Column(name = "user_id")
	private String userId;

}
