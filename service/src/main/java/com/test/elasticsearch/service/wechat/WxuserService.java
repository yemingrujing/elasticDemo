package com.test.elasticsearch.service.wechat;

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
    String getOpenId(String appid, String secret, String jsCode);
}
