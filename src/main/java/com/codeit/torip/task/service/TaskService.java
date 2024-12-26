package com.codeit.torip.task.service;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.common.exception.AlertException;
import com.codeit.torip.task.dto.request.TaskListRequest;
import com.codeit.torip.task.dto.request.TaskModRequest;
import com.codeit.torip.task.dto.request.TaskRegRequest;
import com.codeit.torip.task.dto.response.TaskDetailResponse;
import com.codeit.torip.task.dto.response.TaskProceedStatusResponse;
import com.codeit.torip.task.entity.Task;
import com.codeit.torip.task.entity.TaskAssignee;
import com.codeit.torip.task.repository.assignee.TaskAssigneeRepository;
import com.codeit.torip.task.repository.task.TaskRepository;
import com.codeit.torip.trip.entity.Trip;
import com.codeit.torip.trip.repository.TripRepository;
import com.codeit.torip.user.entity.User;
import com.codeit.torip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public List<TaskDetailResponse> getTaskList(TaskListRequest taskListRequest) {
        // 할일 정보 불러오기
        var taskDetailList = taskRepository.selectTaskDetailList(taskListRequest);
        var taskIdList = taskDetailList.stream().map(TaskDetailResponse::getTaskId).toList();
        // 담당자 정보 불러오기
        var assigneeList = taskAssigneeRepository.selectTaskAssigneeList(taskIdList);
        for (var taskDetail : taskDetailList) {
            var taskId = taskDetail.getTaskId();
            for (var assignee : assigneeList) {
                if (taskId.equals(assignee.getTaskId())) {
                    taskDetail.getTaskAssignees().add(assignee);
                }
            }
        }
        return taskDetailList;
    }

    public TaskDetailResponse getTaskDetail(long taskId) {
        // 할일 정보 불러오기
        var taskDetail = taskRepository.selectTaskDetail(taskId)
                .orElseThrow(() -> new AlertException("할일을 조회할 수 없습니다."));
        // 담당자 정보 불러오기
        var assigneeList = taskAssigneeRepository.selectTaskAssignee(taskId);
        if (taskDetail != null) taskDetail.getTaskAssignees().addAll(assigneeList);
        return taskDetail;
    }

    public TaskProceedStatusResponse getProgressStatus() {
        var taskDetailList = taskRepository.selectAllTaskDetailList();
        // 통계 산정
        var proceedStatus = new TaskProceedStatusResponse();
        for (var taskProceedStatus : taskDetailList) {
            var scope = taskProceedStatus.getTaskScope();
            if (scope.equals(PUBLIC)) proceedStatus.setCommonTask(taskProceedStatus.getTaskCompletionDate());
            else proceedStatus.setPersonalTask(taskProceedStatus.getTaskCompletionDate());
        }
        return proceedStatus;
    }

    @Transactional
    public void completeTask(Long taskId) {
        // 권한 체크
        var isModifiable = taskRepository.isAuthorizedToModify(taskId);
        if (isModifiable) throw new AlertException("할일을 수정할 권한이 없습니다.");
        var taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new AlertException("할일이 존재하지 않습니다."));
        taskEntity.setCompletionDate(LocalDateTime.now());
    }

    @Transactional
    public Long registerTask(TaskRegRequest taskRegRequest) {
        // 권한 체크
        var tripEntity = checkTripMember(taskRegRequest.getTripId());
        // 할일 세팅
        var taskEntity = Task.from(taskRegRequest);
        taskEntity.setTrip(tripEntity);
        tripEntity.addTask(taskEntity);
        var assignees = taskEntity.getAssignees();
        // 담당자 추가
        for (var assignee : taskRegRequest.getTaskAssignees()) {
            var userEntity = userRepository.findUserByEmail(assignee)
                    .orElseThrow(() -> new AlertException("담당자가 존재하지 않습니다."));
            var taskAssigneeEntity = TaskAssignee.builder().task(taskEntity).assignee(userEntity).build();
            assignees.add(taskAssigneeEntity);
        }
        // 할일 등록
        var result = taskRepository.save(taskEntity);
        return result.getId();
    }

    @Transactional
    public Long modifyTask(TaskModRequest taskModRequest) {
        // 권한 체크
        var isModifiable = taskRepository.isAuthorizedToModify(taskModRequest.getTaskId());
        if (isModifiable) throw new AlertException("할일을 수정할 권한이 없습니다.");
        // 할일 수정
        var assignees = taskModRequest.getTaskAssignees();
        var taskEntity = taskRepository.findById(taskModRequest.getTaskId())
                .orElseThrow(() -> new AlertException("할일이 존재하지 않습니다."));
        taskEntity.modifyTo(taskModRequest);
        var result = taskRepository.save(taskEntity);
        // 담당자 조회
        var assigneeModifyList = taskAssigneeRepository.selectTaskModifyAssignee(taskModRequest.getTaskId());
        // 기존 담당자 제거
        for (var assigneeModify : assigneeModifyList) {
            var email = assigneeModify.getEmail();
            var id = assigneeModify.getTaskAssigneeId();
            if (!assignees.contains(email)) taskAssigneeRepository.deleteById(id);
            else assignees.remove(email);
        }
        // 신규 담당자 추가
        var tripEntity = checkTripMember(taskModRequest.getTripId());
        for (String email : assignees) {
            var userEntity = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new AlertException("존재하지 않는 사용자입니다."));
            // 새로 추가될 담당자가 여행 맴버인지 체크
            tripEntity.checkMemberExists(User.builder().email(email).build());
            var assigneeEntity = TaskAssignee.builder().task(taskEntity).assignee(userEntity).build();
            taskAssigneeRepository.save(assigneeEntity);
        }
        return result.getId();
    }

    @Transactional
    public void deleteTask(long taskId) {
        // 권한 체크
        var isDeletable = taskRepository.isAuthorizedToModify(taskId);
        if (!isDeletable) throw new AlertException("할일을 삭제할 권한이 없습니다.");
        // 할일 삭제
        taskRepository.deleteById(taskId);
    }

    private Trip checkTripMember(long tripId) {
        var tripEntity = tripRepository.findByIdWithMembers(tripId)
                .orElseThrow(() -> new AlertException("여행이 존재하지 않습니다."));
        tripEntity.checkMemberExists(AuthUtil.getUserInfo());
        return tripEntity;
    }

}
