package com.test.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guang
 * @title: MyCreateDTO
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/11/323:06
 */
@Setter
@Getter
public class MyVoteCreateDTO {

    private Integer id;

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
