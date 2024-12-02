package com.codeit.torip.note.dto;

import com.codeit.torip.task.entity.TravelStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDetailDto {

    private long noteId;
    private long noteSeq;
    private String travelTitle;
    private TravelStatus travelStatus;
    private String noteTitle;
    private String noteContent;
    private String link;
    private String createdBy;
    private LocalDateTime createdAt;
    private String modifiedBy;
    private LocalDateTime modifiedAt;

}
