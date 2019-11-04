package com.test.elasticsearch.param.wechat;

import lombok.Getter;
import lombok.Setter;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.param.wechat
 * @ClassName: VoteTaskParam
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/4 13:49
 * @Version: 1.0
 */
@Setter
@Getter
public class VoteTaskParam {

    private Integer id;

    private String openId;

    private String voteId;

    private String title;

    private String description;

    private String optionData;

    private String date;

    private String time;

    private String noName;

    private Integer radio;
}
