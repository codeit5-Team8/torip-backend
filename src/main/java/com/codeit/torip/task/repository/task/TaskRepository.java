package com.codeit.torip.task.repository.task;

import com.codeit.torip.task.entity.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long>, CustomTaskRepository {

    @EntityGraph(attributePaths = "assignees")
    @Query("SELECT t FROM Task t WHERE t.id = :taskId")
    Optional<Task> findByIdWithAssignees(@Param("taskId") Long taskId);

}
