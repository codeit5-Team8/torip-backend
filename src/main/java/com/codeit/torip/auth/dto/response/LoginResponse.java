package com.codeit.torip.auth.dto.response;

import com.codeit.torip.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@Builder
@Getter
public class LoginResponse {
    @Schema(description = "사용자의 id", example = "1")
    private Long id;
    @Schema(description = "사용자의 유저네임", example = "test")
    private String username;
    @Schema(description = "사용자의 이메일", example = "test@test.com")
    private String email;
    @Schema(description = "accessToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklkIjoic3RyaW5nIiwidHlwZSI6ImFjY2VzcyIsImlhdCI6MTczMjgwMzI0OSwiZXhwIjoxNzMyODAzODQ5fQ.F3hjvzGHgoahAAUUe3M44UfU8eceSHHdl4LFkH8GBjQ")
    private String accessToken;
    @Schema(description = "refreshToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklkIjoic3RyaW5nIiwidHlwZSI6ImFjY2VzcyIsImlhdCI6MTczMjgwMzI0OSwiZXhwIjoxNzMyODAzODQ5fQ.F3hjvzGHgoahAAUUe3M44UfU8eceSHHdl4LFkH8GBjQ")
    private String refreshToken;
    @Schema(description = "refreshToken 만료 시간", example = "2021-11-01T00:00:00")
    private LocalDateTime expiredAt;
}
