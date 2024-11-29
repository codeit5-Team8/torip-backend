package com.codeit.torip.travel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateTravelRequest {
    @NotEmpty
    @Schema(description = "여행 이름", example = "여행1", nullable = false)
    @Size(min = 1, max = 30)
    String name;
    @NotNull
    @Schema(description = "여행 시작 날짜", example = "test@test.com", nullable = false)
    LocalDate startDate;
    @NotNull
    @Schema(description = "여행 종료 날짜", example = "test@test.com", nullable = false)
    LocalDate endDate;
}
