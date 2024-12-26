package com.codeit.torip.task.note.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.task.entity.Task;
import com.codeit.torip.task.note.dto.request.TaskNoteModRequest;
import com.codeit.torip.task.note.dto.request.TaskNoteRegRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "task_note")
public class TaskNote extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    public static TaskNote from(TaskNoteRegRequest taskNoteRegRequest) {
        return TaskNote.builder()
                .title(taskNoteRegRequest.getNoteTitle())
                .content(taskNoteRegRequest.getNoteContent())
                .build();
    }

    public void modifyTo(TaskNoteModRequest taskNoteModRequest) {
        this.title = taskNoteModRequest.getNoteTitle();
        this.content = taskNoteModRequest.getNoteContent();
    }
}