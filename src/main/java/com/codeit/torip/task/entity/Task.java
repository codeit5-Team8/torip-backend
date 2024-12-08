package com.codeit.torip.task.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.note.entity.Note;
import com.codeit.torip.task.dto.request.TaskRequest;
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
@EqualsAndHashCode(callSuper = false)
@Builder()
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Note> notes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskAssignee> assignees = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskScope scope;

    @Column(nullable = false)
    private LocalDateTime taskDDay;

    private LocalDateTime completionDate;

    public static Task from(TaskRequest taskRequest, Trip trip) {
        return Task.builder()
                .trip(trip)
                .taskDDay(taskRequest.getTaskDDay())
                .title(taskRequest.getTaskTitle())
                .filePath(taskRequest.getFilePath())
                .taskDDay(taskRequest.getTaskDDay())
                .status(taskRequest.getTripStatus())
                .scope(taskRequest.getScope())
                .build();
    }

    public void modifyTo(TaskRequest taskRequest) {
        this.title = taskRequest.getTaskTitle();
        this.filePath = taskRequest.getFilePath();
        this.status = taskRequest.getTripStatus();
        this.taskDDay = taskRequest.getTaskDDay();
        this.scope = taskRequest.getScope();
        this.completionDate = taskRequest.getCompletionDate();
    }

}