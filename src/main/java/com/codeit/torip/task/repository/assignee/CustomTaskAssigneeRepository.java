package com.codeit.torip.task.repository.assignee;

import com.codeit.torip.task.dto.TaskAssigneeDto;
import com.codeit.torip.task.dto.TaskModifyAssigneeDto;

import java.util.List;

public interface CustomTaskAssigneeRepository {

    List<TaskAssigneeDto> selectTaskAssigneeList(long travelId, long seq);

    List<TaskAssigneeDto> selectTaskAssignee(long taskId);

    List<TaskModifyAssigneeDto> selectTaskModifyAssignee(long taskId);

}
