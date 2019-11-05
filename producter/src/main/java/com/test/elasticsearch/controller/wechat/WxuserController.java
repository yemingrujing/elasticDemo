package com.test.elasticsearch.controller.wechat;

import com.test.elasticsearch.param.wechat.UserDataParam;
import com.test.elasticsearch.service.wechat.WxuserService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result getOpenId(@RequestParam String appid,
                            @RequestParam String secret,
                            @RequestParam String jsCode) {
        return ResultUtil.success(wxuserService.getOpenId(appid, secret, jsCode));
    }

    @PostMapping("/application/link/userDataSave")
    public Result userDataSave(@RequestBody UserDataParam param) {
        wxuserService.userDataSave(param);
        return ResultUtil.success();
    }

    @GetMapping("/application/link/getAccessToken")
    public Result getAccessToken(@RequestParam String appid,
                                 @RequestParam String secret) {
        return ResultUtil.success(wxuserService.getAccessToken(appid, secret));
    }
}
