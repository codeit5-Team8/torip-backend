package com.codeit.torip.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class TokenRequest {
    @NotEmpty
    @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklkIjoic3RyaW5nIiwidHlwZSI6ImFjY2VzcyIsImlhdCI6MTczMjgwMzI0OSwiZXhwIjoxNzMyODAzODQ5fQ.F3hjvzGHgoahAAUUe3M44UfU8eceSHHdl4LFkH8GBjQ")
    private String refreshToken;
}
