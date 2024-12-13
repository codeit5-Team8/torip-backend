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

    @NotNull
    @Schema(description = "여행 고유키", example = "1", nullable = false)
    private Long tripId;
    @NotNull
    @Schema(description = "현재 페이지에서 가장 작은 여행 노트 고유키 [ 최초 조회시 0으로 요청 ]", example = "1", nullable = false)
    private Long seq;

}
