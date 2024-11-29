package com.codeit.torip.task.repository;

import com.codeit.torip.task.entity.TaskAssignee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssigneeRepository extends JpaRepository<TaskAssignee, Long> {
}
