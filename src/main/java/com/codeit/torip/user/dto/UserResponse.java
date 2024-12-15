package com.codeit.torip.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponse {
    @Schema(description = "사용자의 id", example = "1")
    private Long id;
    @Schema(description = "사용자의 유저네임", example = "test")
    private String username;
    @Schema(description = "사용자의 이메일", example = "test@test.com")
    private String email;
}
