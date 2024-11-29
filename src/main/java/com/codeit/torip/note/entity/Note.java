package com.codeit.torip.note.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.task.entity.Task;
import jakarta.persistence.*;

@Entity
@Table(name = "note")
public class Note extends BaseUserEntity {
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
}