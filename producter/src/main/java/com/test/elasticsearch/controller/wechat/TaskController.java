package com.test.elasticsearch.controller.wechat;

import com.test.elasticsearch.service.wechat.TaskService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guang
 * @title: TaskController
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/11/323:03
 */
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/application/vote/getMyJoin")
    public Result getMyJoin(String openId) {
        return ResultUtil.success(taskService.getMyJoin(openId));
    }

    @GetMapping("/application/vote/getMyCreate")
    public Result getMyCreate(String openId) {
        return ResultUtil.success(taskService.getMyCreate(openId));
    }

    @GetMapping("/application/vote/getGIDTask")
    public Result getGIDTask(String groupId) {
        return ResultUtil.success(taskService.getGIDTask(groupId));
    }
}
