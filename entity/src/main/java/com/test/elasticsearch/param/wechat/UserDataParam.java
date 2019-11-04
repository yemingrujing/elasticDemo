package com.test.elasticsearch.param.wechat;

import lombok.Getter;
import lombok.Setter;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.param.wechat
 * @ClassName: UserDataParam
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/4 13:44
 * @Version: 1.0
 */
@Setter
@Getter
public class UserDataParam {

    /**
     * 用户唯一标识
     */
    private String openid;

    /**
     * 用户信息：图像
     */
    private String avatarUrl;

    /**
     * 用户信息：所在城市
     */
    private String city;

    /**
     * 用户信息：性别。0:未知，1：男，2：女
     */
    private String gender;

    /**
     * 用户信息：语言
     */
    private String language;

    /**
     * 用户信息：姓名
     */
    private String nickName;

    /**
     * 用户信息：省份
     */
    private String province;
}
