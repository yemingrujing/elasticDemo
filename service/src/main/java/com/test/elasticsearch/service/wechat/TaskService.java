package com.test.elasticsearch.service.wechat;

import com.test.elasticsearch.dto.GIDTaskDTO;
import com.test.elasticsearch.dto.MyCreateDTO;
import com.test.elasticsearch.dto.MyJoinDTO;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat
 * @ClassName: TaskService
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/1 18:18
 * @Version: 1.0
 */
public interface TaskService {

    /**
     * 请求我参加的投票
     * @author  GuangWei
     * @param openId
     * @return  java.util.List<com.test.elasticsearch.dto.MyJoinDTO>
     * @exception
     * @date       2019/11/3 22:55
     */
    List<MyJoinDTO> getMyJoin(String openId);

    /**
     * 请求我创建的接龙列表
     * @author  GuangWei
     * @param openId
     * @return  java.util.List<com.test.elasticsearch.dto.MyCreateDTO>
     * @exception
     * @date       2019/11/3 22:55
     */
    List<MyCreateDTO> getMyCreate(String openId);

    /**
     * 请求我创建的接龙列表
     * @author  GuangWei
     * @param groupId
     * @return  java.util.List<com.test.elasticsearch.dto.GIDTaskDTO>
     * @exception
     * @date       2019/11/3 22:55
     */
    List<GIDTaskDTO> getGIDTask(String groupId);
}
