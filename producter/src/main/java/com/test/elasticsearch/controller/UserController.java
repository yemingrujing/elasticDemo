package com.test.elasticsearch.controller;

import com.test.elasticsearch.entity.mysql.wechat.UserEntity;
import com.test.elasticsearch.service.wechat.UserService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller
 * @ClassName: UserController
 * @Author: guang
 * @Description: 用户controller
 * @Date: 2019/5/30 19:10
 * @Version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/findAll")
    public Result findAll(@RequestParam(required = false) String phone) {
        List<UserEntity> userList = userService.findAll(phone);
        return ResultUtil.success(userList);
    }
}
