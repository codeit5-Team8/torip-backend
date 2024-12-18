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
import java.util.Optional;

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
        var owner = new QUser("owner");
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        var condition = getCommonCondition(assignee);
        var tripId = taskListRequest.getTripId();
        if (tripId != null && tripId != 0) condition = condition.and(trip.id.eq(taskListRequest.getTripId()));
        var seq = taskListRequest.getTaskSeq();
        if (seq != null && seq != 0) condition = condition.and(task.id.lt(seq));
        var status = taskListRequest.getTaskStatus();
        if (status != null) {
            condition = condition.and(task.taskStatus.eq(status));
        }
        var scope = taskListRequest.getTaskScope();
        if (scope != null) {
            condition = condition.and(task.scope.eq(scope));
        }
        var pageSize = taskListRequest.getAll() ? TASK_LIMIT : PAGE_SIZE;
        // 할일 목록 불러오기
        return factory.selectDistinct(
                        Projections.constructor(
                                TaskDetailResponse.class,
                                task.id, trip.name, task.title, task.filePath, task.taskStatus,
                                task.taskDDay, task.scope, task.completionDate,
                                task.lastCreatedUser.username, task.createdAt, task.lastUpdatedUser.username, task.updatedAt
                        ))
                .from(task)
                .join(task.trip, trip)
                .join(trip.owner, owner)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, assignee)
                .join(task.lastCreatedUser, createdBy)
                .join(task.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(task.id.desc())
                .limit(pageSize)
                .fetch();
    }

    @Override
    public Optional<TaskDetailResponse> selectTaskDetail(long taskId) {
        var owner = new QUser("owner");
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        BooleanExpression condition = task.id.eq(taskId);
        condition = condition.and(getCommonCondition(assignee));
        // 할일 상세 불러오기
        var taskDetail = factory.selectDistinct(
                        Projections.constructor(
                                TaskDetailResponse.class,
                                task.id, trip.name, task.title, task.filePath, task.taskStatus,
                                task.taskDDay, task.scope, task.completionDate,
                                task.lastCreatedUser.username, task.createdAt, task.lastUpdatedUser.username, task.updatedAt
                        ))
                .from(task)
                .join(task.trip, trip)
                .join(trip.owner, owner)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, assignee)
                .join(task.lastCreatedUser, createdBy)
                .join(task.lastUpdatedUser, modifiedBy)
                .where(condition)
                .fetchOne();
        return Optional.ofNullable(taskDetail);
    }

    @Override
    public List<TaskProceedStatusDto> selectAllTaskDetailList() {
        var owner = new QUser("owner");
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        // 쿼리 조건 생성
        BooleanExpression condition = getCommonCondition(assignee);
        // 할일 완료도 불러오기
        return factory.selectDistinct(
                        Projections.constructor(
                                TaskProceedStatusDto.class,
                                task.scope, task.completionDate
                        ))
                .from(task)
                .join(task.trip, trip)
                .join(trip.owner, owner)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, assignee)
                .join(task.lastCreatedUser, createdBy)
                .where(condition)
                .fetch();
    }

    @Override
    public boolean isAuthorizedToModify(long taskId) {
        var owner = new QUser("owner");
        var createdBy = new QUser("createdBy");
        // 쿼리 조건 생성
        var email = AuthUtil.getEmail();
        var condition = task.id.eq(taskId);
        condition = condition.and(trip.owner.email.eq(email).or(task.lastCreatedUser.email.eq(email)));
        // 수정 가능 여부 판단
        return factory.selectOne()
                .from(task)
                .join(task.trip, trip)
                .join(trip.owner, owner)
                .join(task.lastCreatedUser, createdBy)
                .where(condition)
                .fetchFirst() != null;
    }

    private BooleanExpression getCommonCondition(QUser assignee) {
        var email = AuthUtil.getEmail();
        // 오너이거나 작성자이거나 담당자인경우
        return trip.owner.email.eq(email).or(task.lastCreatedUser.email.eq(email)).or(assignee.email.eq(email));
    }

}
