package com.test.elasticsearch.entity.mysql.wechat;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_task")
public class TaskEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "open_id")
	private String openId;

	@Column(name = "task_id")
	private String taskId;

	private String title;

	private String date;

	private String time;

	private String address;

	private String name;

	private String tel;

	private String remark;

}
