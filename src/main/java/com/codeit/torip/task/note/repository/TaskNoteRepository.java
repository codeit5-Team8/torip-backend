package com.codeit.torip.task.note.repository;

import com.codeit.torip.task.note.entity.TaskNote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskNoteRepository extends JpaRepository<TaskNote, Long>, CustomTaskNoteRepository {

    @EntityGraph(attributePaths = "task")
    @Query("SELECT tn FROM TaskNote tn WHERE tn.id = :taskNoteId")
    Optional<TaskNote> findByIdWithTask(@Param("taskNoteId") Long taskNoteId);

}