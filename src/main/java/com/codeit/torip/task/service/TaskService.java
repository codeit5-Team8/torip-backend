package com.codeit.torip.task.service;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.task.dto.*;
import com.codeit.torip.task.entity.Task;
import com.codeit.torip.task.entity.TaskAssignee;
import com.codeit.torip.task.repository.assignee.TaskAssigneeRepository;
import com.codeit.torip.task.repository.task.TaskRepository;
import com.codeit.torip.user.entity.QUser;
import com.codeit.torip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.codeit.torip.task.entity.TaskScope.PUBLIC;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskAssigneeRepository taskAssigneeRepository;

    @Transactional
    public void registerTask(TaskDto taskDto) {

        // TODO 내 여행인지 로직 추가
        var email = AuthUtil.getEmail();
        var taskEntity = Task.from(taskDto);
        List<TaskAssignee> assignees = taskEntity.getAssignees();
        for (String assignee : taskDto.getAssignees()) {
            var userEntity = userRepository.findByEmail(assignee).orElseThrow();
            var taskAssigneeEntity = TaskAssignee.builder().task(taskEntity).assignee(userEntity).build();
            assignees.add(taskAssigneeEntity);
        }
        taskRepository.save(taskEntity);
    }

    public List<TaskDetailDto> getTaskList(long travelId, long seq) {

        // TODO 내 여행인지 로직 추가
        var email = AuthUtil.getEmail();
        // 할일 정보 불러오기
        var taskDetailDtoList = taskRepository.selectTaskDetailList(travelId,seq);
        // 담당자 정보 불러오기
        List<TaskAssigneeDto> assigneeDtoList = taskAssigneeRepository.selectTaskAssigneeList(travelId,seq);
        for (TaskDetailDto taskDetail : taskDetailDtoList) {
            var taskId = taskDetail.getTaskId();
            for (TaskAssigneeDto assigneeDto : assigneeDtoList) {
                if (taskId.equals(assigneeDto.getTaskId())) {
                    taskDetail.getAssignees().add(assigneeDto);
                }
            }
        }
        return taskDetailDtoList;
    }

    public TaskDetailDto getTaskDetail(long taskId) {

        // TODO 내 여행인지 로직 추가
        var email = AuthUtil.getEmail();

        QUser createBy = new QUser("createBy");
        QUser modifiedBy = new QUser("modifiedBy");
        // 할일 정보 불러오기
        TaskDetailDto taskDetailDto = taskRepository.selectTaskDetail(taskId);
        // 담당자 정보 불러오기
        List<TaskAssigneeDto> assigneeDtoList = taskAssigneeRepository.selectTaskAssignee(taskId);
        if (taskDetailDto != null) taskDetailDto.getAssignees().addAll(assigneeDtoList);
        return taskDetailDto;
    }

    @Transactional
    public void modifyTask(TaskDto taskDto) {
        List<TaskModifyAssigneeDto> assigneeModifyDtoList =
                taskAssigneeRepository.selectTaskModifyAssignee(taskDto.getTaskId());

        Set<String> assignees = taskDto.getAssignees();
        Task taskEntity = taskRepository.findById(taskDto.getTaskId()).get();

        // 할일 정보 수정
        taskEntity.modifyTo(taskDto);
        taskRepository.save(taskEntity);

        // 기존 담당자 제거
        for (TaskModifyAssigneeDto assigneeModifyDto : assigneeModifyDtoList) {
            String email = assigneeModifyDto.getEmail();
            Long id = assigneeModifyDto.getTaskAssigneeId();
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

        // TODO 나중에 효율적인 쿼리로 리펙토링 하자
        List<TaskDetailDto> taskDetailList = taskRepository.selectAllTaskDetailList(email);
        // 통계 산정
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
