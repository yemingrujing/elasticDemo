package com.test.elasticsearch.controller.wechat;

import com.test.elasticsearch.service.wechat.WxuserService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guang
 * @title: WxuserController
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/11/216:22
 */
@RestController
public class WxuserController {

    @Autowired
    private WxuserService wxuserService;

    @GetMapping("/application/link/getOpenId")
    public Result getOpenId(String appid, String secret, String jsCode) {
        return ResultUtil.success(wxuserService.getOpenId(appid, secret, jsCode));
    }
}
