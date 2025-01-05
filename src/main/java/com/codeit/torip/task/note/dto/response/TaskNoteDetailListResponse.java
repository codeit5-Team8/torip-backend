package com.codeit.torip.task.note.dto.response;

import com.codeit.torip.task.note.dto.NoteDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskNoteDetailListResponse {

    @Schema(description = "여행 고유키", example = "1")
    private Long tripId;
    @Schema(description = "여행 제목", example = "여행 제목")
    private String tripTitle;
    @Schema(description = "할일 고유키", example = "1")
    private Long taskId;
    @Schema(description = "할일 제목", example = "할일 제목")
    private String taskTitle;
    @Schema(description = "할일 모아보기 상세")
    private List<NoteDetailDto> details;

}
