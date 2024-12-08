package com.codeit.torip.task.repository.assignee;

import com.codeit.torip.task.dto.TaskAssigneeDto;
import com.codeit.torip.task.dto.TaskModifyAssigneeDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Task.PAGE_SIZE;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.task.entity.QTaskAssignee.taskAssignee;
import static com.codeit.torip.user.entity.QUser.user;

@RequiredArgsConstructor
public class CustomTaskAssigneeRepositoryImpl implements CustomTaskAssigneeRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<TaskAssigneeDto> selectTaskAssigneeList(List<Long> taskIdList) {
        // 담당자 정보 불러오기
        return factory.select(
                        Projections.constructor(
                                TaskAssigneeDto.class,
                                task.id, user.id, user.email, user.username
                        )
                )
                .from(task)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, user)
                .where(task.id.in(taskIdList))
                .orderBy(task.id.desc())
                .limit(PAGE_SIZE)
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
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, user)
                .where(task.id.eq(taskId))
                .fetch();
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
                .from(task)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, user)
                .where(task.id.eq(taskId))
                .fetch();
    }
}
