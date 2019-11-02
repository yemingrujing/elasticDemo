package com.test.elasticsearch.entity.mysql.wechat;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_vote_chatgroup")
public class VoteChatgroupEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "group_id")
	private String groupId;

	@Column(name = "vote_id")
	private String voteId;

}
