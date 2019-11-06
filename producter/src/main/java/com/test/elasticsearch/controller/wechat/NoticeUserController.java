package com.test.elasticsearch.controller.wechat;

import com.test.elasticsearch.service.wechat.NoticeUserService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller.wechat
 * @ClassName: NoticeUserController
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 11:26
 * @Version: 1.0
 */
@RestController
public class NoticeUserController {

    @Autowired
    private NoticeUserService noticeUserService;

    @GetMapping("/application/notice/myView")
    private Result getMyView(@RequestParam String openId) {
        return ResultUtil.success(noticeUserService.getMyView(openId));
    }
}
