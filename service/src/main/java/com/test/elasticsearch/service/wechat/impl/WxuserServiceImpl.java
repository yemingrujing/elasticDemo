package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.test.elasticsearch.entity.mysql.wechat.WxuserEntity;
import com.test.elasticsearch.param.wechat.UserDataParam;
import com.test.elasticsearch.repository.mysql.wechat.WxuserRepository;
import com.test.elasticsearch.service.wechat.WxuserService;
import com.test.elasticsearch.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author guang
 * @title: WxuserServiceImpl
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/11/214:11
 */
@Service
@Slf4j
public class WxuserServiceImpl implements WxuserService {

    public static String getOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    public static String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    @Autowired
    private WxuserRepository wxuserRepository;

    @Override
    public JSONObject getOpenId(String appid, String secret, String jsCode) {
        log.info("appid: {}, secret: {}, jsCode: {}", appid, secret, jsCode);
        if (StrUtil.isNotBlank(appid) && StrUtil.isNotBlank(secret) && StrUtil.isNotBlank(jsCode)) {
            String url = getOpenIdUrl.replace("APPID", appid).replace("SECRET", secret).replace("JSCODE", jsCode);
            JSONObject jsonObject = HttpUtil.get(url, JSONObject.class);
            String openId = jsonObject.getString("openid");
            if (StrUtil.isNotBlank(openId)) {
                Optional<WxuserEntity> optional = wxuserRepository.findOne(Example.of(WxuserEntity.builder().openId(openId).build()));
                if (!optional.isPresent()) {
                    wxuserRepository.save(WxuserEntity.builder().appId(appid).openId(openId).build());
                }
            }
            return jsonObject;
        }
        return null;
    }

    @Override
    public void userDataSave(UserDataParam param) {
        if (Objects.nonNull(param) && StrUtil.isNotBlank(param.getOpenid())) {
            Optional<WxuserEntity> optional = wxuserRepository.findOne(Example.of(WxuserEntity.builder().openId(param.getOpenid()).build()));
            if (optional.isPresent()) {
                WxuserEntity wxuserEntity = optional.get();
                wxuserEntity.setAvatarUrl(param.getAvatarUrl());
                wxuserEntity.setCity(param.getCity());
                wxuserEntity.setLanguage(param.getLanguage());
                wxuserEntity.setNickName(param.getNickName());
                wxuserEntity.setProvince(param.getProvince());
                wxuserEntity.setTime(DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));
                wxuserRepository.save(wxuserEntity);
            }
        }
    }

    @Override
    public JSONObject getAccessToken(String appid, String secret) {
        if (StrUtil.isNotBlank(appid) && StrUtil.isNotBlank(secret)) {
            String url = getAccessTokenUrl.replace("APPID", appid).replace("APPSECRET", secret);
            JSONObject jsonObject = HttpUtil.get(url, JSONObject.class);
            return jsonObject;
        }
        return null;
    }


}
