package com.codeit.torip.task.repository.task;

import com.codeit.torip.task.dto.TaskProceedStatusDto;
import com.codeit.torip.task.dto.request.TaskListRequest;
import com.codeit.torip.task.dto.response.TaskDetailResponse;

import java.util.List;

public interface CustomTaskRepository {

    List<TaskDetailResponse> selectTaskDetailList(TaskListRequest taskListRequest);

    TaskDetailResponse selectTaskDetail(long taskId);

    List<TaskProceedStatusDto> selectAllTaskDetailList();

}
