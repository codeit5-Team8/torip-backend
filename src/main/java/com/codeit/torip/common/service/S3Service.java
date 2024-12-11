package com.codeit.torip.common.service;

import com.codeit.torip.common.dto.S3UrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3UrlResponse generateS3UploadUrl(String fileName) {
        try {
            // 현재 날짜 기준 디렉토리 경로 생성
            LocalDate now = LocalDate.now();
            String datePath = String.format("%d/%02d/%02d/",
                    now.getYear(),
                    now.getMonthValue(),
                    now.getDayOfMonth()
            );
            // 파일 확장자 추출
            var fileExtension = Optional.ofNullable(fileName)
                    .filter(file -> file.contains("."))
                    .map(file -> file.substring(fileName.lastIndexOf(".")))
                    .orElseThrow();
            // 고유한 파일명 생성 (날짜 디렉토리 포함)
            var uniqueFileName = datePath + UUID.randomUUID() + fileExtension;
            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(uniqueFileName)
                    .build();
            // 서명된 S3 PUT URL 요청
            var presignRequest = s3Presigner.presignPutObject(
                    req -> req.putObjectRequest(putObjectRequest)
                            .signatureDuration(Duration.ofMinutes(1))
            );
            return S3UrlResponse.builder()
                    .signedUrl(presignRequest.url().toString())
                    .filePath(uniqueFileName)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Presigned PUT URL 생성에 실패하였습니다", e);
        }
    }

    public S3UrlResponse generateS3DownloadUrl(String filePath) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(filePath)
                    .build();
            // 서명된 S3 GET URL 요청
            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(
                    req -> req.getObjectRequest(getObjectRequest)
                            .signatureDuration(Duration.ofMinutes(10))
            );
            return S3UrlResponse.builder()
                    .signedUrl(presignedGetObjectRequest.url().toString())
                    .filePath(filePath)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Presigned GET URL 생성에 실패하였습니다", e);
        }
    }

}
