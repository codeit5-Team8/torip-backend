package com.codeit.torip.note.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.note.dto.NoteDto;
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
@Table(name = "note", indexes = {@Index(name = "idx_task_id_seq", columnList = "id, seq")})
public class Note extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, length = 30)
    private String link;

    public static Note from(NoteDto noteDto) {
        return Note.builder()
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .link(noteDto.getLink())
                .seq(noteDto.getNoteSeq() + 1)
                .build();
    }

    public void modifyTo(NoteDto noteDto) {
        this.title = noteDto.getTitle();
        this.content = noteDto.getContent();
        this.link = noteDto.getLink();
    }
}