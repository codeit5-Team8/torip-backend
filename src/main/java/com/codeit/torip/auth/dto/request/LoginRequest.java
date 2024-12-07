package com.codeit.torip.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotEmpty
    @Email
    @Schema(description = "사용자의 이메일", example = "test@test.com", nullable = false)
    private String email;
    @NotEmpty
    @Schema(description = "사용자의 비밀번호", example = "1234", nullable = false)
    private String password;
}
