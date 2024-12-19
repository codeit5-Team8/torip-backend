package com.codeit.torip.trip.note.dto.request;

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
public class TripNoteListRequest {

    @NotNull(message = "여행 고유키는 필수 값입니다.")
    @Schema(description = "여행 고유키", example = "1")
    private Long tripId;
    @Schema(description = "현재 페이지에서 가장 작은 여행 노트 고유키", example = "1", defaultValue = "0")
    private Long tripNoteSeq = 0L;
    @Schema(description = "현재 페이지에서 가장 작은 할일 노트 고유키", example = "1", defaultValue = "0")
    private Long taskNoteSeq = 0L;

}
