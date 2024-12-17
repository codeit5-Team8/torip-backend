package com.codeit.torip.task.note.repository;

import com.codeit.torip.task.note.entity.TaskNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskNoteRepository extends JpaRepository<TaskNote, Long>, CustomTaskNoteRepository {
}
