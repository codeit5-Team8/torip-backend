package com.codeit.torip.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3UploadLinkResponse {

    @Schema(description = "미리 서명된 S3 링크", example = "https://torip.s3.ap-northeast-2.amazonaws.com/c79dffc9...")
    private String signedUrl;

    @Schema(description = "저장된 파일 경로", example = "2024/01/11/uuid-filename.jpg")
    private String filePath;
}
