package com.codeit.torip.task.repository.task;

import com.codeit.torip.task.dto.TaskDetailDto;

import java.util.List;

public interface CustomTaskRepository {

    List<TaskDetailDto> selectTaskDetailList(long travelId, long seq);

    TaskDetailDto selectTaskDetail(long taskId);

    List<TaskDetailDto> selectAllTaskDetailList(String email);

}
