package com.codeit.torip.task.note.dto.response;

import com.codeit.torip.task.entity.TripStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskNoteDetailResponse {

    @Schema(description = "할일 노트 고유키", example = "1")
    private Long taskNoteId;
    @Schema(description = "여행 제목", example = "여행 제목")
    private String tripTitle;
    @Schema(description = "할일 제목", example = "할일 제목")
    private String taskTitle;
    @Schema(description = "할일 여행 단계", example = "BEFORE_TRIP")
    private TripStatus tripStatus;
    @Schema(description = "할일 노트 제목", example = "할일 노트 제목")
    private String taskNoteTitle;
    @Schema(description = "할일 노트 내용", example = "할일 노트 내용")
    private String taskNoteContent;
    @Schema(description = "할일 노트 등록자", example = "demo@gmail.com")
    private String createdBy;
    @Schema(description = "할일 노트 등록일", example = "2024-10-11 15:21:00")
    private LocalDateTime createdAt;
    @Schema(description = "할일 노트 수정자", example = "demo@gmail.com")
    private String modifiedBy;
    @Schema(description = "할일 노트 수정일", example = "2024-10-11 15:21:00")
    private LocalDateTime modifiedAt;

}
