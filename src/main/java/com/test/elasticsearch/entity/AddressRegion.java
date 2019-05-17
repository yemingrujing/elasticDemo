package com.test.elasticsearch.entity;

import lombok.Data;

/**
 * 省市区地址dao接口
 *
 * @author Wei.Guang
 * @create 2019-04-19 23:30
 **/
@Data
public class AddressRegion {

    private Integer id;

    private Long province_id;

    private Long parent_id;

    private String name;

    private String merger_name;

    private String short_name;

    private String merger_short_name;

    private Long level_type;

    private String city_code;

    private String zip_code;

    private String pinyin;

    private String jianpin;

    private String firstchar;

    private String create_time;
}
