package com.test.elasticsearch.controller.wechat;

import com.test.elasticsearch.param.wechat.VoteTaskParam;
import com.test.elasticsearch.service.wechat.VoteTaskService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller.wechat
 * @ClassName: VoteTaskController
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/4 14:02
 * @Version: 1.0
 */
@RestController
public class VoteTaskController {

    @Autowired
    private VoteTaskService voteTaskService;

    @PostMapping("/application/vote/createVoteTask")
    public Result createVoteTask(@RequestBody VoteTaskParam param) {
        voteTaskService.createVoteTask(param);
        return ResultUtil.success();
    }

    @GetMapping("/application/vote/getVoteTask")
    public Result getVoteTask(@RequestParam String voteId) {
        return ResultUtil.success(voteTaskService.getVoteTask(voteId));
    }

    @PostMapping("/application/vote/storeVoteOne")
    public Result storeVoteOne(@RequestParam String voteId,
                               @RequestParam String optionData,
                               @RequestParam String openId) {
        voteTaskService.storeVoteOne(voteId, optionData, openId);
        return ResultUtil.success();
    }
}
