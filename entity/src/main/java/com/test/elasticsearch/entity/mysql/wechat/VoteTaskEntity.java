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
@Table(name = "t_vote_task")
public class VoteTaskEntity implements Serializable {

  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "open_id")
	private String openId;

	@Column(name = "vote_id")
	private String voteId;

	private String title;

	private String description;

	@Column(name = "option_data")
	private String optionData;

	private String date;

	private String time;

	@Column(name = "no_name")
	private String noName;

	private Integer radio;

}
