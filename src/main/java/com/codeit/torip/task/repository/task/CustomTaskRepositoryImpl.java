package com.codeit.torip.task.repository.task;

import com.codeit.torip.task.dto.TaskDetailDto;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Task.PAGE_OFFSET;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.task.entity.QTaskAssignee.taskAssignee;
import static com.codeit.torip.travel.entity.QTravel.travel;
import static com.codeit.torip.user.entity.QUser.user;

@RequiredArgsConstructor
public class CustomTaskRepositoryImpl implements CustomTaskRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<TaskDetailDto> selectTaskDetailList(long travelId, long seq) {
        var createBy = new QUser("createBy");
        var modifiedBy = new QUser("modifiedBy");
        // 할일 정보 불러오기
        return factory.select(
                        Projections.constructor(
                                TaskDetailDto.class,
                                task.id, travel.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate, task.createBy.email,
                                task.createdAt, task.modifiedBy.email, task.updatedAt
                        ))
                .from(task)
                .join(task.travel, travel)
                .join(task.createBy, createBy)
                .join(task.modifiedBy, modifiedBy)
                .where(
                        travel.id.eq(travelId).and(task.id.lt(seq))
                ).orderBy(task.id.desc())
                .limit(PAGE_OFFSET)
                .fetch();
    }

    @Override
    public TaskDetailDto selectTaskDetail(long taskId) {
        var createBy = new QUser("createBy");
        var modifiedBy = new QUser("modifiedBy");
        // 할일 정보 불러오기
        return factory.select(
                        Projections.constructor(
                                TaskDetailDto.class,
                                task.id, travel.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate, task.createBy.email,
                                task.createdAt, task.modifiedBy.email, task.updatedAt
                        ))
                .from(task)
                .join(task.travel, travel)
                .join(task.createBy, createBy)
                .join(task.modifiedBy, modifiedBy)
                .where(
                        task.id.eq(taskId)
                ).fetchOne();
    }

    @Override
    public List<TaskDetailDto> selectAllTaskDetailList(String email) {
        var createBy = new QUser("createBy");
        var modifiedBy = new QUser("modifiedBy");
        return factory.select(
                        Projections.constructor(
                                TaskDetailDto.class,
                                task.id, travel.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate, task.createBy.email, task.createdAt,
                                task.modifiedBy.email, task.updatedAt
                        ))
                .from(taskAssignee)
                .join(taskAssignee.task, task)
                .join(task.travel, travel)
                .join(task.createBy, createBy)
                .join(task.modifiedBy, modifiedBy)
                .where(user.email.eq(email))
                .fetch();
    }

}
