package com.test.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guang
 * @title: MyJoinDTO
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/11/322:47
 */
@Setter
@Getter
public class MyJoinDTO {

    private Integer id;

    private String taskId;

    private String userId;

    private String viewerId;

    private String openId;

    private String title;

    private String date;

    private String time;

    private String address;

    private String name;

    private String tel;

    private String remark;
}
