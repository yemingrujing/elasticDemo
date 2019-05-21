package com.test.elasticsearch.utils.enums;

import lombok.Getter;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.utils.enums
 * @ClassName: ResultEnum
 * @Author: guang
 * @Description: 枚举类ResultEnum
 * @Date: 2019/5/21 10:19
 * @Version: 1.0
 */
@Getter
public enum ResultEnum {

    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(200, "成功"),
    ERROR(99999, "系统错误");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
