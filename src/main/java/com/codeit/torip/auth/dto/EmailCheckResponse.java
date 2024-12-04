package com.codeit.torip.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailCheckResponse {
    @Schema(description = "이메일 중복 여부", example = "true")
    private Boolean exists;
}
