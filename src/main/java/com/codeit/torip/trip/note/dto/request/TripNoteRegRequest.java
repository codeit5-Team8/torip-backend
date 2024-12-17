package com.codeit.torip.trip.note.dto.request;

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
public class TripNoteRegRequest {

    @NotNull
    @Schema(description = "여행 고유키", example = "1", nullable = false)
    private long tripId;
    @NotBlank
    @Max(value = 30, message = "여행 노트 제목은 30자를 초과하실 수 없습니다")
    @Schema(description = "여행 노트 제목", example = "여행 노트 제목", nullable = false)
    private String taskNoteTitle;
    @NotBlank
    @Schema(description = "여행 노트 내용", example = "여행 노트 내용", nullable = false)
    private String taskNoteContent;

}
