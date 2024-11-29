package com.codeit.torip.task.entity;

import com.codeit.torip.common.entity.BaseEntity;
import com.codeit.torip.note.entity.Note;
import com.codeit.torip.travel.entity.Travel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskScope scope;

    @Column(nullable = false)
    private LocalDateTime taskDDay;

    @Column(nullable = false)
    private LocalDateTime completionDate;
}