package com.test.elasticsearch.entity.mysql.wechat;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_user")
public class UserEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "task_id")
	private String taskId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "viewer_id")
	private String viewerId;

}
