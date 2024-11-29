package com.codeit.torip.task.service;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.task.dto.RegisterTaskDto;
import com.codeit.torip.task.entity.Task;
import com.codeit.torip.task.entity.TaskAssignee;
import com.codeit.torip.task.entity.TaskScope;
import com.codeit.torip.task.entity.TravelStatus;
import com.codeit.torip.task.repository.TaskAssigneeRepository;
import com.codeit.torip.task.repository.TaskRepository;
import com.codeit.torip.travel.entity.Travel;
import com.codeit.torip.travel.repository.TravelRepository;
import com.codeit.torip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaskService {

    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskAssigneeRepository taskAssigneeRepository;

    @Transactional
    public void registerTask(RegisterTaskDto registerTaskDto) {

        // TODO 내 여행인지 로직 추가
        var email = AuthUtil.getEmail();

        var taskEntity = Task.builder()
                .scope(TaskScope.valueOf(registerTaskDto.getScope()))
                .taskDDay(registerTaskDto.getTaskDDay())
                .title(registerTaskDto.getTaskTitle())
                .status(TravelStatus.valueOf(registerTaskDto.getTaskStatus()))
                .travel(Travel.builder().id(registerTaskDto.getTravelId()).build())
                .build();
        
        for (String assignee : registerTaskDto.getAssigneeList()) {
            var userEntity = userRepository.findByUsername(assignee);
            var taskAssigneeEntity = TaskAssignee.builder()
                    .task(taskEntity)
                    .assignee(userEntity.orElseThrow())
                    .build();
            taskAssigneeRepository.save(taskAssigneeEntity);
        }
    }

}
