package com.codeit.torip.task.note.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskNoteDeletedResponse {

    @Schema(description = "여행 고유키", example = "1")
    private Long tripId;
    @Schema(description = "할일 노트 제목", example = "할일 노트 제목")
    private String noteTitle;
    @Schema(description = "할일 노트 내용", example = "할일 노트 내용")
    private String noteContent;

}
