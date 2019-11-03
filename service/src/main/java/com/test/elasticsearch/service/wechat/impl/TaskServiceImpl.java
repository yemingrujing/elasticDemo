package com.test.elasticsearch.service.wechat.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.elasticsearch.dto.GIDTaskDTO;
import com.test.elasticsearch.dto.MyCreateDTO;
import com.test.elasticsearch.dto.MyJoinDTO;
import com.test.elasticsearch.entity.mysql.wechat.QChatgroupEntity;
import com.test.elasticsearch.entity.mysql.wechat.QTaskEntity;
import com.test.elasticsearch.entity.mysql.wechat.QUserEntity;
import com.test.elasticsearch.repository.mysql.wechat.TaskRepository;
import com.test.elasticsearch.service.wechat.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guang
 * @title: TaskServiceImpl
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/11/322:46
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // JPA查询工厂
    private JPAQueryFactory queryFactory;

    @Override
    public List<MyJoinDTO> getMyJoin(String openId) {
        QUserEntity userEntity = QUserEntity.userEntity;
        QTaskEntity taskEntity = QTaskEntity.taskEntity;
        JPAQuery<MyJoinDTO> query = queryFactory.select(
                Projections.bean(
                        MyJoinDTO.class,
                        taskEntity.id,
                        taskEntity.taskId,
                        userEntity.userId,
                        userEntity.viewerId,
                        taskEntity.openId,
                        taskEntity.title,
                        taskEntity.date,
                        taskEntity.time,
                        taskEntity.address,
                        taskEntity.name,
                        taskEntity.tel,
                        taskEntity.remark
                )
        )
                .from(userEntity)
                .leftJoin(taskEntity)
                .on(userEntity.taskId.eq(taskEntity.taskId))
                .where(userEntity.userId.eq(openId));
        return query.fetchResults().getResults();
    }

    @Override
    public List<MyCreateDTO> getMyCreate(String openId) {
        QTaskEntity taskEntity = QTaskEntity.taskEntity;
        JPAQuery<MyCreateDTO> query = queryFactory.select(
                Projections.bean(
                        MyCreateDTO.class,
                        taskEntity.id,
                        taskEntity.taskId,
                        taskEntity.openId,
                        taskEntity.title,
                        taskEntity.date,
                        taskEntity.time,
                        taskEntity.address,
                        taskEntity.name,
                        taskEntity.tel,
                        taskEntity.remark
                )
        )
                .from(taskEntity)
                .where(taskEntity.openId.eq(openId));
        return query.fetchResults().getResults();
    }

    @Override
    public List<GIDTaskDTO> getGIDTask(String groupId) {
        QChatgroupEntity chatgroupEntity = QChatgroupEntity.chatgroupEntity;
        QTaskEntity taskEntity = QTaskEntity.taskEntity;
        JPAQuery<GIDTaskDTO> query = queryFactory.select(
                Projections.bean(
                        GIDTaskDTO.class,
                        taskEntity.id,
                        chatgroupEntity.groupId,
                        taskEntity.taskId,
                        taskEntity.openId,
                        taskEntity.title,
                        taskEntity.date,
                        taskEntity.time,
                        taskEntity.address,
                        taskEntity.name,
                        taskEntity.tel,
                        taskEntity.remark
                )
        )
                .from(chatgroupEntity)
                .leftJoin(taskEntity)
                .on(chatgroupEntity.taskId.eq(taskEntity.taskId))
                .where(chatgroupEntity.groupId.eq(groupId));
        return query.fetchResults().getResults();
    }
}
