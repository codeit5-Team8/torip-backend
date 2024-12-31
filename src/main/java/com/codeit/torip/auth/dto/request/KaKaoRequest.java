package com.codeit.torip.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class KaKaoRequest {
    @NotEmpty
    @Schema(description = "카카오톡 인증 코드", example = "8V3gV0llzTKXaGQR_QOx4n11nokMcxiIvBob7Hsng-UfDLgaandX3gAAAAQKKcjZAAABlA6A1jvgLMgnBn6ZSw")
    private String code;
}
