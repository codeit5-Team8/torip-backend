package com.codeit.torip.task.repository.task;

import com.codeit.torip.task.dto.TaskDetailDto;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Task.PAGE_SIZE;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.task.entity.QTaskAssignee.taskAssignee;
import static com.codeit.torip.travel.entity.QTravel.travel;

@RequiredArgsConstructor
public class CustomTaskRepositoryImpl implements CustomTaskRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<TaskDetailDto> selectTaskDetailList(long travelId, long seq) {
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        BooleanExpression condition = travel.id.eq(travelId);
        if (seq != 0) condition = condition.and(task.id.lt(seq));
        // 할일 정보 불러오기
        return factory.select(
                        Projections.constructor(
                                TaskDetailDto.class,
                                task.id, travel.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate,
                                task.lastcreatedUser.email, task.createdAt, task.lastUpdatedUser.email, task.updatedAt
                        ))
                .from(task)
                .join(task.travel, travel)
                .join(task.lastcreatedUser, createdBy)
                .join(task.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(task.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    @Override
    public TaskDetailDto selectTaskDetail(long taskId) {
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 할일 정보 불러오기
        return factory.select(
                        Projections.constructor(
                                TaskDetailDto.class,
                                task.id, travel.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate,
                                task.lastcreatedUser.email, task.createdAt, task.lastUpdatedUser.email, task.updatedAt
                        ))
                .from(task)
                .join(task.travel, travel)
                .join(task.lastcreatedUser, createdBy)
                .join(task.lastUpdatedUser, modifiedBy)
                .where(
                        task.id.eq(taskId)
                ).fetchOne();
    }

    @Override
    public List<TaskDetailDto> selectAllTaskDetailList(String email) {
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        return factory.select(
                        Projections.constructor(
                                TaskDetailDto.class,
                                task.id, travel.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate,
                                task.lastcreatedUser.email, task.createdAt, task.lastUpdatedUser.email, task.updatedAt
                        ))
                .from(taskAssignee)
                .join(taskAssignee.task, task)
                .join(task.travel, travel)
                .join(task.lastcreatedUser, createdBy)
                .join(task.lastUpdatedUser, modifiedBy)
                .where(taskAssignee.assignee.email.eq(email))
                .fetch();
    }

}
