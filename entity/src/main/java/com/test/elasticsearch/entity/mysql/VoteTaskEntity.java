package com.test.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_vote_task")
public class VoteTaskEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String openid;

	private String voteid;

	private String title;

	private String description;

	@Column(name = "optionData")
	private String optiondata;

	private String date;

	private String time;

	@Column(name = "noName")
	private String noname;

	private Integer radio;

}
