package com.codeit.torip.task.dto;

import com.codeit.torip.task.entity.TaskScope;
import com.codeit.torip.task.entity.TravelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailDto {

    @Schema(description = "할일 고유키", example = "1")
    private Long taskId;
    @Schema(description = "할일 시퀀스", example = "1")
    private Long taskSeq;
    @Schema(description = "여행 제목", example = "여행 제목")
    private String travelName;
    @Schema(description = "할일 제목", example = "할일 제목")
    private String taskTitle;
    @Schema(description = "할일 파일 경로", example = "/home/image/sample.jpg")
    private String taskFilePath;
    @Schema(description = "할일 여행 단계", example = "BEFORE_TRAVEL / DURING_TRAVEL / AFTER_TRAVEL")
    private TravelStatus taskStatus;
    @Schema(description = "할일 D-Day", example = "2024-10-11 15:21:00")
    private LocalDateTime taskDDay;
    @Schema(description = "할일 공유 범위", example = "PUBLIC / PRIVATE")
    private TaskScope taskScope;
    @Schema(description = "할일 완료일", example = "2024-10-11 15:21:00")
    private LocalDateTime taskCompletionDate;
    @Schema(description = "할일 등록자", example = "demo@gmail.com")
    private String taskCreatedBy;
    @Schema(description = "할일 등록일", example = "2024-10-11 15:21:00")
    private LocalDateTime taskCreatedAt;
    @Schema(description = "할일 수정자", example = "demo@gmail.com")
    private String taskModifiedBy;
    @Schema(description = "할일 수정일", example = "2024-10-11 15:21:00")
    private LocalDateTime taskUpdatedAt;

    // TODO 담당자 목록 추가 개발 필요
    private List<String> assignees = new ArrayList<>();

    public TaskDetailDto(Long taskId, Long taskSeq, String travelName, String taskTitle, String taskFilePath, TravelStatus taskStatus, LocalDateTime taskDDay, TaskScope taskScope, LocalDateTime taskCompletionDate, String taskCreatedBy, LocalDateTime taskCreatedAt, String taskModifiedBy, LocalDateTime taskUpdatedAt) {
        this.taskId = taskId;
        this.taskSeq = taskSeq;
        this.travelName = travelName;
        this.taskTitle = taskTitle;
        this.taskFilePath = taskFilePath;
        this.taskStatus = taskStatus;
        this.taskDDay = taskDDay;
        this.taskScope = taskScope;
        this.taskCompletionDate = taskCompletionDate;
        this.taskCreatedBy = taskCreatedBy;
        this.taskCreatedAt = taskCreatedAt;
        this.taskModifiedBy = taskModifiedBy;
        this.taskUpdatedAt = taskUpdatedAt;
    }
}
