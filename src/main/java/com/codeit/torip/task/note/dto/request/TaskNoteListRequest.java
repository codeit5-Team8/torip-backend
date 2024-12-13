package com.codeit.torip.task.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskNoteListRequest {

    @NotNull
    @Schema(description = "할일 고유키", example = "1", nullable = false)
    private Long taskId;
    @NotNull
    @Schema(description = "현재 페이지에서 가장 작은 할일 노트 고유키 [ 최초 조회시 0으로 요청 ]", example = "1", nullable = false)
    private Long seq;

}