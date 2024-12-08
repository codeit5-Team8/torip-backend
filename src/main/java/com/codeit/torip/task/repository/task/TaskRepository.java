package com.codeit.torip.task.repository.task;

import com.codeit.torip.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, CustomTaskRepository {
}
