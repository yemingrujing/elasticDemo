package com.test.elasticsearch.utils;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.utils
 * @ClassName: Result
 * @Author: guang
 * @Description: 封装返回数据的统一格式
 * @Date: 2019/5/21 10:10
 * @Version: 1.0
 */
public class Result<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体的内容. */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
