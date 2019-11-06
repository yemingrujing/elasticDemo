package com.test.elasticsearch.dto.wechat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guang
 * @title: GIDTaskDTO
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/11/323:11
 */
@Setter
@Getter
public class VoteGIDTaskDTO {

    private Integer id;

    private String groupId;

    private String openId;

    private String voteId;

    private String title;

    private String description;

    private String optionData;

    private String date;

    private String time;

    private String noName;

    private Integer radio;
}
