package com.test.elasticsearch.service.wechat;

import com.alibaba.fastjson.JSONObject;
import com.test.elasticsearch.param.wechat.UserDataParam;

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
     * @param param
     * @return  void
     * @exception  
     * @date       2019/11/2 21:57
     */
    void userDataSave(UserDataParam param);

    /**
     * 获取AccessToken
     * @author  GuangWei
     * @param appid 小程序 appId
     * @param secret 小程序 appSecret
     * @return  java.lang.String
     * @exception
     * @date       2019/11/2 14:13
     */
    JSONObject getAccessToken(String appid, String secret);
}
