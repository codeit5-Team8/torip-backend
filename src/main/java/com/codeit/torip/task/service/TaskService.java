package com.codeit.torip.task.service;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.task.dto.TaskDetailDto;
import com.codeit.torip.task.dto.TaskDto;
import com.codeit.torip.task.dto.TaskProceedStatusDto;
import com.codeit.torip.task.entity.Task;
import com.codeit.torip.task.entity.TaskAssignee;
import com.codeit.torip.task.repository.TaskAssigneeRepository;
import com.codeit.torip.task.repository.TaskRepository;
import com.codeit.torip.travel.entity.Travel;
import com.codeit.torip.user.entity.QUser;
import com.codeit.torip.user.repository.UserRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.codeit.torip.common.contant.ToripConstants.Task.PAGE_OFFSET;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.task.entity.QTaskAssignee.taskAssignee;
import static com.codeit.torip.task.entity.TaskScope.PUBLIC;
import static com.codeit.torip.travel.entity.QTravel.travel;
import static com.codeit.torip.user.entity.QUser.user;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaskService {

    private final JPAQueryFactory factory;

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskAssigneeRepository taskAssigneeRepository;

    @Transactional
    public void registerTask(TaskDto taskDto) {

        // TODO 내 여행인지 로직 추가
        var email = AuthUtil.getEmail();

        var taskEntity = Task.builder()
                .taskDDay(taskDto.getTaskDDay())
                .title(taskDto.getTaskTitle())
                .filePath(taskDto.getFilePath())
                .taskDDay(taskDto.getTaskDDay())
                .status(taskDto.getTravelStatus())
                .scope(taskDto.getScope())
                .travel(Travel.builder().id(taskDto.getTravelId()).build())
                .build();

        List<TaskAssignee> assignees = new ArrayList<>();
        for (String assignee : taskDto.getAssignees()) {
            var userEntity = userRepository.findByEmail(assignee).orElseThrow();
            var taskAssigneeEntity = TaskAssignee.builder()
                    .task(taskEntity)
                    .assignee(userEntity)
                    .build();
            assignees.add(taskAssigneeEntity);
        }

        taskEntity.setAssignees(assignees);
        taskRepository.save(taskEntity);
    }

    public List<TaskDetailDto> getTaskList(long travelId, long seq) {
        var email = AuthUtil.getEmail();
        QUser createBy = new QUser("createBy");
        QUser modifiedBy = new QUser("modifiedBy");
        var result = factory.select(
                        Projections.constructor(
                                TaskDetailDto.class,
                                task.id, task.seq, travel.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate, task.createBy.email,
                                task.createdAt, task.modifiedBy.email, task.updatedAt
                        ))
                .from(taskAssignee)
                .join(taskAssignee.task, task)
                .join(task.travel, travel)
                .join(task.createBy, createBy)
                .join(task.modifiedBy, modifiedBy)
                .where(
                        travel.id.eq(travelId).and(task.seq.between(seq, seq + PAGE_OFFSET)).and(user.email.eq(email))
                ).fetch();
        return result;
    }

    public TaskDetailDto getTaskDetail(long taskId) {
        var email = AuthUtil.getEmail();
        QUser createBy = new QUser("createBy");
        QUser modifiedBy = new QUser("modifiedBy");
        return factory.select(
                        Projections.constructor(
                                TaskDetailDto.class,
                                task.id, task.seq, travel.name, task.title, task.filePath, task.status,
                                task.taskDDay, task.scope, task.completionDate, task.createBy.email,
                                task.createdAt, task.modifiedBy.email, task.updatedAt
                        ))
                .from(taskAssignee)
                .join(taskAssignee.task, task)
                .join(task.travel, travel)
                .join(task.createBy, createBy)
                .join(task.modifiedBy, modifiedBy)
                .where(
                        task.id.eq(taskId).and(user.email.eq(email))
                ).fetchOne();
    }

    @Transactional
    public void modifyTask(TaskDto taskDto) {
        List<Tuple> tuples = factory.select(
                        taskAssignee.id,
                        user.email
                ).from(taskAssignee)
                .join(taskAssignee.assignee, user)
                .join(taskAssignee.task, task)
                .where(
                        task.id.eq(taskDto.getTaskId())
                ).fetch();

        Set<String> assignees = taskDto.getAssignees();
        Task taskEntity = taskRepository.findById(taskDto.getTaskId()).get();

        // 할일 정보 수정
        taskEntity.modifyTo(taskDto);
        taskRepository.save(taskEntity);

        // 기존 담당자 제거
        for (Tuple tuple : tuples) {
            String email = tuple.get(user.email);
            Long id = tuple.get(taskAssignee.id);
            if (!assignees.contains(email)) taskAssigneeRepository.deleteById(id);
            else assignees.remove(email);
        }

        // 신규 담당자 추가
        for (String email : assignees) {
            var userEntity = userRepository.findByEmail(email).get();
            TaskAssignee assigneeEntity = TaskAssignee.builder().task(taskEntity).assignee(userEntity).build();
            taskAssigneeRepository.save(assigneeEntity);
        }
    }

    public TaskProceedStatusDto getProgressStatus() {
        var email = AuthUtil.getEmail();
        QUser createBy = new QUser("createBy");
        QUser modifiedBy = new QUser("modifiedBy");

        // TODO 나중에 효율적인 쿼리로 리펙토링 하자
        List<TaskDetailDto> taskDetailList = factory.select(
                        Projections.constructor(
                                TaskDetailDto.class,
                                task.id, task.seq, travel.name, task.title, task.filePath, task.status,
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

        var proceedStatus = new TaskProceedStatusDto();
        for (TaskDetailDto taskDetail : taskDetailList) {
            var scope = taskDetail.getTaskScope();
            if (scope.equals(PUBLIC)) proceedStatus.setCommonTask(taskDetail.getTaskCompletionDate());
            else proceedStatus.setPersonalTask(taskDetail.getTaskCompletionDate());
        }
        return proceedStatus;
    }

    @Transactional
    public void deleteTask(long taskId) {
        taskRepository.deleteById(taskId);
    }

}
