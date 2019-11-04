package com.test.elasticsearch.service.wechat;

import com.test.elasticsearch.param.wechat.VoteTaskParam;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat
 * @ClassName: VoteTaskService
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/1 18:18
 * @Version: 1.0
 */
public interface VoteTaskService {

    void createVoteTask(VoteTaskParam param);
}
