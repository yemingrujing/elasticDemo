package com.test.elasticsearch.entity.mysql.wechat;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_vote_user")
public class VoteUserEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "vote_id")
	private String voteId;

	@Column(name = "user_id")
	private String userId;

}
