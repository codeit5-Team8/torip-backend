package com.codeit.torip.trip.note.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripNoteDetailDto {

    @Schema(description = "여행 노트 고유키", example = "1")
    private Long tripNoteId;
    @Schema(description = "여행 노트 제목", example = "여행 노트 제목")
    private String tripNoteTitle;
    @Schema(description = "여행 노트 내용", example = "여행 노트 내용")
    private String tripNoteContent;
    @Schema(description = "여행 노트 등록자", example = "홍길동")
    private String createdBy;
    @Schema(description = "여행 노트 등록일", example = "2024-10-11 15:21:00")
    private LocalDateTime createdAt;
    @Schema(description = "여행 노트 수정자", example = "홍길동")
    private String modifiedBy;
    @Schema(description = "여행 노트 수정일", example = "2024-10-11 15:21:00")
    private LocalDateTime modifiedAt;

}
