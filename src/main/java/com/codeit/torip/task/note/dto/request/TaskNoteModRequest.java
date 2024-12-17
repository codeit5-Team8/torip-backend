package com.codeit.torip.task.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskNoteModRequest {

    @NotNull
    @Schema(description = "할일 노트 고유키", example = "1")
    private Long taskNoteId;
    @NotBlank
    @Max(value = 30, message = "할일 노트 제목은 30자를 초과하실 수 없습니다")
    @Schema(description = "할일 노트 제목", example = "할일 노트 제목", nullable = false)
    private String taskNoteTitle;
    @NotBlank
    @Schema(description = "할일 노트 내용", example = "할일 노트 내용", nullable = false)
    private String taskNoteContent;

}
