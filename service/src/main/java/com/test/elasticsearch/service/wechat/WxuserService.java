package com.test.elasticsearch.service.wechat;

import com.alibaba.fastjson.JSONObject;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat
 * @ClassName: WxuserService
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/1 18:19
 * @Version: 1.0
 */
public interface WxuserService {

    /**
     * 获取OpenId
     * @author  GuangWei
     * @param appid 小程序 appId
     * @param secret 小程序 appSecret
     * @param jsCode 登录时获取的 code
     * @return  java.lang.String
     * @exception
     * @date       2019/11/2 14:13
     */
    JSONObject getOpenId(String appid, String secret, String jsCode);
    
    /**
     * 将登陆的用户详情信息存储到数据库
     * @author  GuangWei
     * @param openid 用户唯一标识
     * @param avatarUrl 用户信息：图像
     * @param city 用户信息：所在城市
     * @param gender 用户信息：性别。0:未知，1：男，2：女
     * @param language 用户信息：语言
     * @param nickName 用户信息：姓名
     * @param province 用户信息：省份
     * @return  void
     * @exception  
     * @date       2019/11/2 21:57
     */
    void userDataSave(String openid, String avatarUrl, String city, String gender, String language, String nickName, String province);
}
