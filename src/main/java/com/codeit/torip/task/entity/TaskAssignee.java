package com.codeit.torip.task.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "task_assignee")
public class TaskAssignee extends BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유키 (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task; // 할일 PK (FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee; // 담당자 (FK)
}
