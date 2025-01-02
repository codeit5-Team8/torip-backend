package com.codeit.torip.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class KaKaoRequest {
    @NotEmpty
    @Schema(description = "카카오톡 access 토큰", example = "1mZjuChPUQxNX9jMyIFkcDDbnSlbUXNEAAAAAQorDNQAAAGUJZV6WabXH4eeWQ3B")
    private String accessToken;
}
