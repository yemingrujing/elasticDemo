package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.elasticsearch.dto.wechat.AllViewerDTO;
import com.test.elasticsearch.dto.wechat.NoticeViewDTO;
import com.test.elasticsearch.entity.mysql.wechat.NoticeUserEntity;
import com.test.elasticsearch.entity.mysql.wechat.QNoticeTaskEntity;
import com.test.elasticsearch.entity.mysql.wechat.QNoticeUserEntity;
import com.test.elasticsearch.entity.mysql.wechat.QWxuserEntity;
import com.test.elasticsearch.repository.mysql.wechat.NoticeUserRepository;
import com.test.elasticsearch.service.wechat.NoticeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat.impl
 * @ClassName: NoticeUserServiceImpl
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 10:40
 * @Version: 1.0
 */
@Service
public class NoticeUserServiceImpl implements NoticeUserService {

    @Autowired
    private NoticeUserRepository noticeUserRepository;

    @Autowired
    private EntityManager entityManager;

    // JPA查询工厂
    private JPAQueryFactory queryFactory;

    @PostConstruct
    private void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<NoticeViewDTO> getMyView(String openId) {
        if (StrUtil.isBlank(openId)) {
            return Lists.newArrayList();
        }
        QNoticeUserEntity noticeUserEntity = QNoticeUserEntity.noticeUserEntity;
        QNoticeTaskEntity noticeTaskEntity = QNoticeTaskEntity.noticeTaskEntity;
        JPAQuery<NoticeViewDTO> query = queryFactory.select(
                Projections.bean(
                        NoticeViewDTO.class,
                        noticeUserEntity.id,
                        noticeUserEntity.noticeId,
                        noticeUserEntity.userId,
                        noticeTaskEntity.openId,
                        noticeTaskEntity.date,
                        noticeTaskEntity.fileNumber,
                        noticeTaskEntity.title,
                        noticeTaskEntity.description,
                        noticeTaskEntity.name
                )
        )
                .from(noticeUserEntity)
                .leftJoin(noticeTaskEntity)
                .on(noticeUserEntity.noticeId.eq(noticeTaskEntity.noticeId))
                .where(noticeUserEntity.userId.eq(openId));
        return query.fetchResults().getResults();
    }

    @Override
    public void storeViewerInfor(String openId, String noticeId) {
        Optional<NoticeUserEntity> optional = noticeUserRepository.findOne(Example.of(NoticeUserEntity.builder().userId(openId).noticeId(noticeId).build()));
        if (!optional.isPresent()) {
            noticeUserRepository.save(NoticeUserEntity.builder().userId(openId).noticeId(noticeId).build());
        }
    }

    @Override
    public List<AllViewerDTO> getAllViewer(String noticeId) {
        if (StrUtil.isBlank(noticeId)) {
            return Lists.newArrayList();
        }
        QNoticeUserEntity noticeUserEntity = QNoticeUserEntity.noticeUserEntity;
        QWxuserEntity wxuserEntity = QWxuserEntity.wxuserEntity;
        JPAQuery<AllViewerDTO> query = queryFactory.select(
                Projections.bean(
                        AllViewerDTO.class,
                        noticeUserEntity.id,
                        noticeUserEntity.noticeId,
                        noticeUserEntity.userId,
                        wxuserEntity.openId,
                        wxuserEntity.appId,
                        wxuserEntity.arcId,
                        wxuserEntity.avatarUrl,
                        wxuserEntity.city,
                        wxuserEntity.language,
                        wxuserEntity.nickName,
                        wxuserEntity.province,
                        wxuserEntity.telNumber,
                        wxuserEntity.uName,
                        wxuserEntity.time,
                        wxuserEntity.joinerName,
                        wxuserEntity.joinerTel,
                        wxuserEntity.joinerRemark
                )
        )
                .from(noticeUserEntity)
                .leftJoin(wxuserEntity)
                .on(noticeUserEntity.userId.eq(wxuserEntity.openId))
                .where(noticeUserEntity.noticeId.eq(noticeId));
        return null;
    }
}
