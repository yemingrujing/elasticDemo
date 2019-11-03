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
public class MyCreateDTO {

    private Integer id;

    private String openId;

    private String taskId;

    private String title;

    private String date;

    private String time;

    private String address;

    private String name;

    private String tel;

    private String remark;
}
