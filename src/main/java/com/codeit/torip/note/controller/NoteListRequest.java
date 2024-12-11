package com.codeit.torip.note.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteListRequest {

    @NotEmpty
    @Schema(description = "여행/할일 필터링 구분", example = "TRIP / TASK", nullable = false)
    private String key;
    @NotNull
    @Schema(description = "여행/할일 고유키", example = "1", nullable = false)
    private Long id;
    @NotNull
    @Schema(description = "현재 페이지에서 가장 작은 노트 고유키 [ 최초 조회시 0으로 요청 ]", example = "1", nullable = false)
    private Long seq;

}
