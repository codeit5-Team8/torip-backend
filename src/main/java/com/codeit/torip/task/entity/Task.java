package com.codeit.torip.task.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.note.entity.Note;
import com.codeit.torip.task.dto.TaskDto;
import com.codeit.torip.travel.entity.Travel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder()
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "task", indexes = {@Index(name = "idx_task_id_seq", columnList = "id, seq")})
public class Task extends BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskAssignee> assignees = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskScope scope;

    @Column(nullable = false)
    private LocalDateTime taskDDay;

    private LocalDateTime completionDate;

    public static Task from(TaskDto taskDto) {
        return Task.builder()
                .taskDDay(taskDto.getTaskDDay())
                .title(taskDto.getTaskTitle())
                .filePath(taskDto.getFilePath())
                .taskDDay(taskDto.getTaskDDay())
                .status(taskDto.getTravelStatus())
                .scope(taskDto.getScope())
                .travel(Travel.builder().id(taskDto.getTravelId()).build())
                .seq(taskDto.getNoteSeq())
                .build();
    }

    public void modifyTo(TaskDto taskDto) {
        this.title = taskDto.getTaskTitle();
        this.filePath = taskDto.getFilePath();
        this.status = taskDto.getTravelStatus();
        this.taskDDay = taskDto.getTaskDDay();
        this.scope = taskDto.getScope();
        this.completionDate = taskDto.getCompletionDate();
    }

}