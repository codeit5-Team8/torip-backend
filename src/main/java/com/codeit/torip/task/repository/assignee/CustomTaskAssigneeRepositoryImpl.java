package com.codeit.torip.task.repository.assignee;

import com.codeit.torip.task.dto.TaskAssigneeDto;
import com.codeit.torip.task.dto.TaskModifyAssigneeDto;
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
public class CustomTaskAssigneeRepositoryImpl implements CustomTaskAssigneeRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<TaskAssigneeDto> selectTaskAssigneeList(long travelId, long seq) {
        return factory.select(
                        Projections.constructor(
                                TaskAssigneeDto.class,
                                task.id, user.id, user.email, user.username
                        )
                )
                .from(task)
                .join(task.travel, travel)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, user)
                .where(
                        travel.id.eq(travelId).and(task.id.lt(seq))
                ).orderBy(task.id.desc())
                .limit(PAGE_OFFSET)
                .fetch();
    }

    @Override
    public List<TaskAssigneeDto> selectTaskAssignee(long taskId) {
        return factory.select(
                        Projections.constructor(
                                TaskAssigneeDto.class,
                                task.id, user.id, user.email, user.username
                        )
                )
                .from(task)
                .join(task.travel, travel)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, user)
                .where(
                        task.id.eq(taskId)
                ).fetch();
    }

    @Override
    public List<TaskModifyAssigneeDto> selectTaskModifyAssignee(long taskId) {
        return factory.select(
                        Projections.constructor(
                                TaskModifyAssigneeDto.class,
                                taskAssignee.id,
                                user.email
                        )
                )
                .from(taskAssignee)
                .join(taskAssignee.assignee, user)
                .join(taskAssignee.task, task)
                .where(
                        task.id.eq(taskId)
                ).fetch();
    }
}
