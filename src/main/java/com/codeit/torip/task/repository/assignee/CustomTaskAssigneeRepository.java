package com.codeit.torip.task.repository.assignee;

import com.codeit.torip.task.dto.TaskAssigneeDto;
import com.codeit.torip.task.dto.TaskModifyAssigneeDto;

import java.util.List;

public interface CustomTaskAssigneeRepository {

    List<TaskAssigneeDto> selectTaskAssigneeList(List<Long> taskIdList);

    List<TaskAssigneeDto> selectTaskAssignee(long taskId);

    List<TaskModifyAssigneeDto> selectTaskModifyAssignee(long taskId);

}
