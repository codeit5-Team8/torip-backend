package com.codeit.torip.trip.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateTripRequest {
    @Schema(description = "여행 이름", example = "여행1", nullable = true)
    String name;
    @Schema(description = "여행 시작 날짜", example = "2024-02-12", nullable = true)
    LocalDate startDate;
    @Schema(description = "여행 종료 날짜", example = "2024-02-20", nullable = true)
    LocalDate endDate;
}
