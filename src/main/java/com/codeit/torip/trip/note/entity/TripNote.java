package com.codeit.torip.trip.note.entity;

import com.codeit.torip.common.entity.BaseUserEntity;
import com.codeit.torip.trip.entity.Trip;
import com.codeit.torip.trip.note.dto.request.TripNoteModRequest;
import com.codeit.torip.trip.note.dto.request.TripNoteRegRequest;
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
@Table(name = "trip_note")
public class TripNote extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 3000)
    private String content;

    public static TripNote from(TripNoteRegRequest tripNoteRegRequest) {
        return TripNote.builder()
                .title(tripNoteRegRequest.getNoteTitle())
                .content(tripNoteRegRequest.getNoteContent())
                .build();
    }

    public void modifyTo(TripNoteModRequest tripNoteModRequest) {
        this.title = tripNoteModRequest.getNoteTitle();
        this.content = tripNoteModRequest.getNoteContent();
    }
}