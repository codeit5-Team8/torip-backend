package com.codeit.torip.task.repository.task;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.task.dto.TaskProceedStatusDto;
import com.codeit.torip.task.dto.request.TaskListRequest;
import com.codeit.torip.task.dto.response.TaskDetailResponse;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Task.PAGE_SIZE;
import static com.codeit.torip.common.contant.ToripConstants.Task.TASK_LIMIT;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.task.entity.QTaskAssignee.taskAssignee;
import static com.codeit.torip.trip.entity.QTrip.trip;

@RequiredArgsConstructor
public class CustomTaskRepositoryImpl implements CustomTaskRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<TaskDetailResponse> selectTaskDetailList(TaskListRequest taskListRequest) {
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        BooleanExpression condition = getCondition(assignee);
        condition = condition.and(trip.id.eq(taskListRequest.getTripId()));
        var seq = taskListRequest.getSeq();
        if (seq != 0) condition = condition.and(task.id.lt(seq));
        var status = taskListRequest.getTripStatus();
        if (status != null) {
            condition = condition.and(task.status.eq(status));
        }
        var scope = taskListRequest.getScope();
        if (scope != null) {
            condition = condition.and(task.status.eq(status));
        }
        var pageSize = taskListRequest.getAll() ? TASK_LIMIT : PAGE_SIZE;
        // 할일 정보 불러오기
        return factory.select(
                        Projections.constructor(
                                TaskDetailResponse.class,
                                task.id, trip.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate,
                                task.lastcreatedUser.username, task.createdAt, task.lastUpdatedUser.username, task.updatedAt
                        ))
                .from(task)
                .join(task.trip, trip)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, assignee)
                .join(task.lastcreatedUser, createdBy)
                .join(task.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(task.id.desc())
                .limit(pageSize)
                .fetch();
    }

    @Override
    public TaskDetailResponse selectTaskDetail(long taskId) {
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        BooleanExpression condition = getCondition(assignee);
        condition = condition.and(task.id.eq(taskId));
        // 할일 정보 불러오기
        return factory.select(
                        Projections.constructor(
                                TaskDetailResponse.class,
                                task.id, trip.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate,
                                task.lastcreatedUser.username, task.createdAt, task.lastUpdatedUser.username, task.updatedAt
                        ))
                .from(task)
                .join(task.trip, trip)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, assignee)
                .join(task.lastcreatedUser, createdBy)
                .join(task.lastUpdatedUser, modifiedBy)
                .where(condition)
                .fetchOne();
    }

    @Override
    public List<TaskProceedStatusDto> selectAllTaskDetailList() {
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        BooleanExpression condition = getCondition(assignee);
        // 할일 목록 불러오기
        return factory.select(
                        Projections.constructor(
                                TaskProceedStatusDto.class,
                                task.scope, task.completionDate
                        ))
                .from(taskAssignee)
                .join(taskAssignee.task, task)
                .join(task.trip, trip)
                .join(task.lastcreatedUser, createdBy)
                .join(task.lastUpdatedUser, modifiedBy)
                .where(condition)
                .fetch();
    }

    private BooleanExpression getCondition(QUser assignee) {
        return assignee.email.eq(AuthUtil.getEmail());
    }

}
