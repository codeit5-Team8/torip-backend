package com.codeit.torip.note.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.note.dto.request.NoteModRequest;
import com.codeit.torip.note.dto.request.NoteRegRequest;
import com.codeit.torip.task.entity.Task;
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

    public static Note from(NoteRegRequest noteRegRequest) {
        return Note.builder()
                .title(noteRegRequest.getTitle())
                .content(noteRegRequest.getContent())
                .build();
    }

    public void modifyTo(NoteModRequest noteModRequest) {
        this.title = noteModRequest.getTitle();
        this.content = noteModRequest.getContent();
    }
}