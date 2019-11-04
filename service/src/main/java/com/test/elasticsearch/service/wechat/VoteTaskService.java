package com.test.elasticsearch.service.wechat;

import com.test.elasticsearch.dto.MyVoteCreateDTO;
import com.test.elasticsearch.param.wechat.VoteTaskParam;

import java.util.List;

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

    /**
     * 请求投票任务的详情数据
     * @param voteId
     * @return
     */
    List<MyVoteCreateDTO> getVoteTask(String voteId);

    /**
     * 保存用户投票数据
     * @param voteId
     * @param optionData
     * @param openId
     */
    void storeVoteOne(String voteId, String optionData, String openId);
}
