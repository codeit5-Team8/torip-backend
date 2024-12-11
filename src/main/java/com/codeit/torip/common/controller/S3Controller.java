package com.codeit.torip.common.controller;

import com.codeit.torip.common.dto.CommonResponse;
import com.codeit.torip.common.dto.S3UrlResponse;
import com.codeit.torip.common.exception.AlertException;
import com.codeit.torip.common.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/torip/s3")
@Tag(name = "S3", description = "S3 관련 API")
public class S3Controller {

    private final AtomicInteger UPLOAD_REQUEST_CNT = new AtomicInteger(0);
    private final AtomicInteger DOWNLOAD_REQUEST_CNT = new AtomicInteger(0);

    private final S3Service s3Service;

    @GetMapping("/upload")
    @Operation(summary = "S3 업로드 URL 발급 API", description = "S3 업로드 URL을 발급합니다. (유효시간 1분)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<S3UrlResponse> generateS3UploadUrl(@RequestParam("fileName") String fileName) {
        // TODO 나중에 지워도 되지만 혹시나 많은 양의 업로드 요청시 비용 문제가 될 수 있으므로 임시 방편으로 제한을 둠
        UPLOAD_REQUEST_CNT.set(UPLOAD_REQUEST_CNT.get() + 1);
        if (UPLOAD_REQUEST_CNT.get() >= 100) throw new AlertException("일일 업로드 제한 100회를 초과하였습니다");

        var s3UrlResponse = s3Service.generateS3UploadUrl(fileName);
        return new CommonResponse<S3UrlResponse>().success(s3UrlResponse);
    }

    @GetMapping("/download")
    @Operation(summary = "S3 다운로드 URL 발급 API", description = "S3 다운로드 URL을 발급합니다. (유효시간 10분)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<S3UrlResponse> generateS3DownloadUrl(@RequestParam("filePath") String filePath) {
        // TODO 나중에 지워도 되지만 혹시나 많은 양의 다운로드 요청시 비용 문제가 될 수 있으므로 임시 방편으로 제한을 둠
        DOWNLOAD_REQUEST_CNT.set(DOWNLOAD_REQUEST_CNT.get() + 1);
        if (DOWNLOAD_REQUEST_CNT.get() >= 1000) throw new AlertException("일일 다운로드 제한 1000회를 초과하였습니다");

        var s3UrlResponse = s3Service.generateS3DownloadUrl(filePath);
        return new CommonResponse<S3UrlResponse>().success(s3UrlResponse);
    }

}
