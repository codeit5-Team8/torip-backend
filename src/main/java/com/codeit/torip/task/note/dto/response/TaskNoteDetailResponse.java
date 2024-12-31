package com.codeit.torip.task.note.dto.response;

import com.codeit.torip.task.entity.TaskStatus;
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
    private Long noteId;
    @Schema(description = "여행 오너 아이디", example = "1")
    private Long ownerId;
    @Schema(description = "여행 제목", example = "여행 제목")
    private String tripTitle;
    @Schema(description = "할일 여행 단계", example = "BEFORE_TRIP")
    private TaskStatus taskStatus;
    @Schema(description = "할일 제목", example = "할일 제목")
    private String taskTitle;
    @Schema(description = "할일 노트 제목", example = "할일 노트 제목")
    private String noteTitle;
    @Schema(description = "할일 노트 내용", example = "할일 노트 내용")
    private String noteContent;
    @Schema(description = "노트 등록자 아이디", example = "1")
    private Long registrantId;
    @Schema(description = "할일 노트 등록자", example = "홍길동")
    private String createdBy;
    @Schema(description = "할일 노트 등록일", example = "2024-10-11 15:21:00")
    private LocalDateTime createdAt;
    @Schema(description = "할일 노트 수정자", example = "홍길동")
    private String modifiedBy;
    @Schema(description = "할일 노트 수정일", example = "2024-10-11 15:21:00")
    private LocalDateTime modifiedAt;

}
