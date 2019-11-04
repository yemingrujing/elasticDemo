package com.test.elasticsearch.controller.wechat;

import com.test.elasticsearch.param.wechat.VoteTaskParam;
import com.test.elasticsearch.service.wechat.VoteTaskService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
