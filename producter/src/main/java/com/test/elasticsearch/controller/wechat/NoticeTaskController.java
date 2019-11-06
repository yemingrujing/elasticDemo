package com.test.elasticsearch.controller.wechat;

import com.test.elasticsearch.param.wechat.NoticeTaskParam;
import com.test.elasticsearch.service.wechat.NoticeTaskService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller.wechat
 * @ClassName: NoticeTaskController
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 11:25
 * @Version: 1.0
 */
@RestController
public class NoticeTaskController {

    @Autowired
    private NoticeTaskService noticeTaskService;

    @GetMapping("/application/notice/myCreate")
    private Result myCreate(@RequestParam String openId) {
        return ResultUtil.success(noticeTaskService.getMyCreateNotice(openId));
    }

    @PostMapping("/application/notice/createNoticeTask")
    private Result createNoticeTask(@RequestBody NoticeTaskParam param) {
        noticeTaskService.createNoticeTask(param);
        return ResultUtil.success();
    }
}
