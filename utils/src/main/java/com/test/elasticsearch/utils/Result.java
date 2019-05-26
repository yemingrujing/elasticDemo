package com.test.elasticsearch.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.utils
 * @ClassName: Result
 * @Author: guang
 * @Description: 封装返回数据的统一格式
 * @Date: 2019/5/21 10:10
 * @Version: 1.0
 */
@Setter
@Getter
public class Result<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体的内容. */
    private T data;
}
