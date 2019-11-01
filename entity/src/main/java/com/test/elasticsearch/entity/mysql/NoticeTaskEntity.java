package com.test.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_notice_task")
public class NoticeTaskEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String openid;

	private String noticeid;

	private String date;

	@Column(name = "fileNumber")
	private String filenumber;

	private String title;

	private String description;

	private String name;

}
