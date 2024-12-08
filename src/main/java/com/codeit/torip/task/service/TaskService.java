package com.codeit.torip.task.service;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.task.dto.request.TaskListRequest;
import com.codeit.torip.task.dto.request.TaskRequest;
import com.codeit.torip.task.dto.response.TaskDetailResponse;
import com.codeit.torip.task.dto.response.TaskProceedStatusResponse;
import com.codeit.torip.task.entity.Task;
import com.codeit.torip.task.entity.TaskAssignee;
import com.codeit.torip.task.repository.assignee.TaskAssigneeRepository;
import com.codeit.torip.task.repository.task.TaskRepository;
import com.codeit.torip.trip.entity.Trip;
import com.codeit.torip.trip.repository.TripRepository;
import com.codeit.torip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codeit.torip.task.entity.TaskScope.PUBLIC;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskAssigneeRepository taskAssigneeRepository;
    private final TripRepository tripRepository;

    @Transactional
    public Long registerTask(TaskRequest taskRequest) {
        Trip trip = tripRepository.findTripById(taskRequest.getTripId()).orElseThrow(() -> new IllegalArgumentException("여행이 존재하지 않습니다."));

        var email = AuthUtil.getEmail();
        var taskEntity = Task.from(taskRequest, trip);

        trip.addTask(taskEntity);

        var assignees = taskEntity.getAssignees();
        // 담당자 추가
        for (var assignee : taskRequest.getAssignees()) {
            var userEntity = userRepository.findUserByEmail(assignee).orElseThrow(() -> new IllegalArgumentException("담당자가 존재하지 않습니다."));
            var taskAssigneeEntity = TaskAssignee.builder().task(taskEntity).assignee(userEntity).build();
            assignees.add(taskAssigneeEntity);
        }
        // 할일 등록
        var result = taskRepository.save(taskEntity);
        return result.getId();
    }

    public List<TaskDetailResponse> getTaskList(TaskListRequest taskListRequest) {
        // 할일 정보 불러오기
        var taskDetailDtoList = taskRepository.selectTaskDetailList(taskListRequest);
        var taskIdList = taskDetailDtoList.stream().map(TaskDetailResponse::getTaskId).toList();
        // 담당자 정보 불러오기
        var assigneeDtoList = taskAssigneeRepository.selectTaskAssigneeList(taskIdList);
        for (var taskDetail : taskDetailDtoList) {
            var taskId = taskDetail.getTaskId();
            for (var assigneeDto : assigneeDtoList) {
                if (taskId.equals(assigneeDto.getTaskId())) {
                    taskDetail.getAssignees().add(assigneeDto);
                }
            }
        }
        return taskDetailDtoList;
    }

    public TaskDetailResponse getTaskDetail(long taskId) {
        // 할일 정보 불러오기
        var taskDetailDto = taskRepository.selectTaskDetail(taskId);
        // 담당자 정보 불러오기
        var assigneeDtoList = taskAssigneeRepository.selectTaskAssignee(taskId);
        if (taskDetailDto != null) taskDetailDto.getAssignees().addAll(assigneeDtoList);
        return taskDetailDto;
    }

    @Transactional
    public Long modifyTask(TaskRequest taskRequest) {
        // 할일 수정
        var assignees = taskRequest.getAssignees();
        var taskEntity = taskRepository.findById(taskRequest.getTaskId()).get();
        taskEntity.modifyTo(taskRequest);
        var result = taskRepository.save(taskEntity);
        // 담당자 조회
        var assigneeModifyDtoList = taskAssigneeRepository.selectTaskModifyAssignee(taskRequest.getTaskId());
        // 기존 담당자 제거
        for (var assigneeModifyDto : assigneeModifyDtoList) {
            var email = assigneeModifyDto.getEmail();
            var id = assigneeModifyDto.getTaskAssigneeId();
            if (!assignees.contains(email)) taskAssigneeRepository.deleteById(id);
            else assignees.remove(email);
        }
        // 신규 담당자 추가
        for (String email : assignees) {
            var userEntity = userRepository.findUserByEmail(email).get();
            var assigneeEntity = TaskAssignee.builder().task(taskEntity).assignee(userEntity).build();
            taskAssigneeRepository.save(assigneeEntity);
        }
        return result.getId();
    }

    public TaskProceedStatusResponse getProgressStatus() {
        // TODO 나중에 효율적인 쿼리로 리펙토링 하자
        var taskDetailList = taskRepository.selectAllTaskDetailList();
        // 통계 산정
        var proceedStatus = new TaskProceedStatusResponse();
        for (var taskDetail : taskDetailList) {
            var scope = taskDetail.getTaskScope();
            if (scope.equals(PUBLIC)) proceedStatus.setCommonTask(taskDetail.getTaskCompletionDate());
            else proceedStatus.setPersonalTask(taskDetail.getTaskCompletionDate());
        }
        return proceedStatus;
    }

    @Transactional
    public void deleteTask(long taskId) {
        // 할일 삭제
        taskRepository.deleteById(taskId);
    }

}
