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
@Table(name = "t_notice_task")
public class NoticeTaskEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "open_id")
	private String openId;

	@Column(name = "notice_id")
	private String noticeId;

	private String date;

	@Column(name = "file_number")
	private String fileNumber;

	private String title;

	private String description;

	private String name;

}
