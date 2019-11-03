package com.test.elasticsearch.dto;

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
public class GIDTaskDTO {

    private Integer id;

    private String groupId;

    private String taskId;

    private String openId;

    private String title;

    private String date;

    private String time;

    private String address;

    private String name;

    private String tel;

    private String remark;
}
