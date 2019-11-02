package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.util.StrUtil;
import com.test.elasticsearch.repository.mysql.wechat.WxuserRepository;
import com.test.elasticsearch.service.wechat.WxuserService;
import com.test.elasticsearch.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author guang
 * @title: WxuserServiceImpl
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/11/214:11
 */
@Service
public class WxuserServiceImpl implements WxuserService {

    public static String getOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    @Autowired
    private WxuserRepository wxuserRepository;

    @Override
    public String getOpenId(String appid, String secret, String jsCode) {
        if (StrUtil.isNotBlank(appid) && StrUtil.isNotBlank(secret) && StrUtil.isNotBlank(jsCode)) {
            getOpenIdUrl.replace("APPID", appid).replace("SECRET", secret).replace("JSCODE", jsCode);
            return HttpUtil.get(getOpenIdUrl);
        }
        return null;
    }
}
