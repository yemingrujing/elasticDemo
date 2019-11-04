package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.elasticsearch.dto.*;
import com.test.elasticsearch.entity.mysql.wechat.*;
import com.test.elasticsearch.repository.mysql.wechat.TaskRepository;
import com.test.elasticsearch.service.wechat.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
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

    @Autowired
    private EntityManager entityManager;

    // JPA查询工厂
    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<MyLinkJoinDTO> getMyLinkJoin(String openId) {
        if (StrUtil.isBlank(openId)) {
            return Lists.newArrayList();
        }
        QUserEntity userEntity = QUserEntity.userEntity;
        QTaskEntity taskEntity = QTaskEntity.taskEntity;
        JPAQuery<MyLinkJoinDTO> query = queryFactory.select(
                Projections.bean(
                        MyLinkJoinDTO.class,
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
    public List<MyVoteJoinDTO> getMyVoteJoin(String openId) {
        if (StrUtil.isBlank(openId)) {
            return Lists.newArrayList();
        }
        QVoteTaskEntity voteTaskEntity = QVoteTaskEntity.voteTaskEntity;
        QVoteUserEntity voteUserEntity = QVoteUserEntity.voteUserEntity;
        JPAQuery<MyVoteJoinDTO> query = queryFactory.select(
                Projections.bean(
                        MyVoteJoinDTO.class,
                        voteTaskEntity.id,
                        voteUserEntity.userId,
                        voteTaskEntity.openId,
                        voteTaskEntity.voteId,
                        voteTaskEntity.title,
                        voteTaskEntity.description,
                        voteTaskEntity.optionData,
                        voteTaskEntity.date,
                        voteTaskEntity.time,
                        voteTaskEntity.noName,
                        voteTaskEntity.radio
                )
        )
                .from(voteTaskEntity)
                .leftJoin(voteUserEntity)
                .on(voteTaskEntity.voteId.eq(voteUserEntity.voteId))
                .where(voteTaskEntity.openId.eq(openId));
        return query.fetchResults().getResults();
    }

    @Override
    public List<MyLinkCreateDTO> getMyLinkCreate(String openId) {
        if (StrUtil.isBlank(openId)) {
            return Lists.newArrayList();
        }
        QTaskEntity taskEntity = QTaskEntity.taskEntity;
        JPAQuery<MyLinkCreateDTO> query = queryFactory.select(
                Projections.bean(
                        MyLinkCreateDTO.class,
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
    public List<MyVoteCreateDTO> getMyVoteCreate(String openId) {
        if (StrUtil.isBlank(openId)) {
            return Lists.newArrayList();
        }
        QVoteTaskEntity voteTaskEntity = QVoteTaskEntity.voteTaskEntity;
        JPAQuery<MyVoteCreateDTO> query = queryFactory.select(
                Projections.bean(
                        MyVoteCreateDTO.class,
                        voteTaskEntity.id,
                        voteTaskEntity.openId,
                        voteTaskEntity.voteId,
                        voteTaskEntity.title,
                        voteTaskEntity.description,
                        voteTaskEntity.optionData,
                        voteTaskEntity.date,
                        voteTaskEntity.time,
                        voteTaskEntity.noName,
                        voteTaskEntity.radio
                )
        )
                .from(voteTaskEntity)
                .where(voteTaskEntity.openId.eq(openId));
        return query.fetchResults().getResults();
    }

    @Override
    public List<LinkGIDTaskDTO> getLinkGIDTask(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return Lists.newArrayList();
        }
        QChatgroupEntity chatgroupEntity = QChatgroupEntity.chatgroupEntity;
        QTaskEntity taskEntity = QTaskEntity.taskEntity;
        JPAQuery<LinkGIDTaskDTO> query = queryFactory.select(
                Projections.bean(
                        LinkGIDTaskDTO.class,
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

    @Override
    public List<VoteGIDTaskDTO> getVoteGIDTask(String groupId) {
        if (StrUtil.isBlank(groupId)) {
            return Lists.newArrayList();
        }
        QVoteTaskEntity voteTaskEntity = QVoteTaskEntity.voteTaskEntity;
        QVoteChatgroupEntity voteChatgroupEntity = QVoteChatgroupEntity.voteChatgroupEntity;
        JPAQuery<VoteGIDTaskDTO> query = queryFactory.select(
                Projections.bean(
                        VoteGIDTaskDTO.class,
                        voteTaskEntity.id,
                        voteChatgroupEntity.groupId,
                        voteTaskEntity.openId,
                        voteTaskEntity.voteId,
                        voteTaskEntity.title,
                        voteTaskEntity.description,
                        voteTaskEntity.optionData,
                        voteTaskEntity.date,
                        voteTaskEntity.time,
                        voteTaskEntity.noName,
                        voteTaskEntity.radio
                )
        )
                .from(voteChatgroupEntity)
                .leftJoin(voteTaskEntity)
                .on(voteChatgroupEntity.voteId.eq(voteTaskEntity.voteId))
                .where(voteChatgroupEntity.groupId.eq(groupId));
        return query.fetchResults().getResults();
    }
}
