package com.test.elasticsearch.dto.wechat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * @author guang
 * @title: MyJoinDTO
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/11/322:47
 */
@Setter
@Getter
public class MyVoteJoinDTO {

    private Integer id;

    private String userId;

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
