package com.codeit.torip.task.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.task.dto.request.TaskModRequest;
import com.codeit.torip.task.dto.request.TaskRegRequest;
import com.codeit.torip.task.note.entity.TaskNote;
import com.codeit.torip.trip.entity.Trip;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "task", indexes = {@Index(name = "idx_scope", columnList = "scope")})
public class Task extends BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskNote> notes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskAssignee> assignees = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskScope scope;

    @Column(nullable = false)
    private LocalDateTime taskDDay;

    private LocalDateTime completionDate;

    public static Task from(TaskRegRequest taskRegRequest) {
        return Task.builder()
                .taskDDay(taskRegRequest.getTaskDDay())
                .title(taskRegRequest.getTaskTitle())
                .filePath(taskRegRequest.getTaskFilePath())
                .taskDDay(taskRegRequest.getTaskDDay())
                .taskStatus(taskRegRequest.getTaskStatus())
                .scope(taskRegRequest.getTaskScope())
                .completionDate(taskRegRequest.getTaskCompletionDate())
                .build();
    }

    public void modifyTo(TaskModRequest taskModRequest) {
        this.title = taskModRequest.getTaskTitle();
        this.filePath = taskModRequest.getTaskFilePath();
        this.taskStatus = taskModRequest.getTaskStatus();
        this.taskDDay = taskModRequest.getTaskDDay();
        this.scope = taskModRequest.getTaskScope();
        this.completionDate = taskModRequest.getTaskCompletionDate();
    }

}