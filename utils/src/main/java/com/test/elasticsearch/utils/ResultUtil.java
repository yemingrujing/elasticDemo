package com.test.elasticsearch.utils;

import com.test.elasticsearch.utils.enums.ResultEnum;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.utils
 * @ClassName: ResultUtil
 * @Author: guang
 * @Description: 统一返回值
 * @Date: 2019/5/21 9:57
 * @Version: 1.0
 */
public class ResultUtil {

    public static Result success() {
        return success(null);
    }
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg("成功");
        result.setData(object);
        return result;
    }
    public static Result success(Integer code, Object object) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result error( String msg) {
        Result result = new Result();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMsg(msg);
        return result;
    }
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
