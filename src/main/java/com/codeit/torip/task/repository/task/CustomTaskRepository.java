package com.codeit.torip.task.repository.task;

import com.codeit.torip.task.dto.response.TaskDetailResponse;
import com.codeit.torip.task.dto.request.TaskListRequest;

import java.util.List;

public interface CustomTaskRepository {

    List<TaskDetailResponse> selectTaskDetailList(TaskListRequest taskListRequest);

    TaskDetailResponse selectTaskDetail(long taskId);

    List<TaskDetailResponse> selectAllTaskDetailList();

}
