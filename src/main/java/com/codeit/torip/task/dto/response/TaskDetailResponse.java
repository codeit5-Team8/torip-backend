package com.codeit.torip.task.dto.response;

import com.codeit.torip.task.dto.TaskAssigneeDto;
import com.codeit.torip.task.entity.TaskScope;
import com.codeit.torip.task.entity.TaskStatus;
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
public class TaskDetailResponse {

    @Schema(description = "할일 고유키", example = "1")
    private Long taskId;
    @Schema(description = "여행 제목", example = "여행 제목")
    private String tripName;
    @Schema(description = "할일 제목", example = "할일 제목")
    private String taskTitle;
    @Schema(description = "할일 파일 경로", example = "/home/image/sample.jpg")
    private String taskFilePath;
    @Schema(description = "할일 여행 단계", example = "BEFORE_TRIP / DURING_TRIP / AFTER_TRIP")
    private TaskStatus taskStatus;
    @Schema(description = "할일 D-Day", example = "2024-10-11 15:21:00")
    private LocalDateTime taskDDay;
    @Schema(description = "할일 공유 범위", example = "PUBLIC / PRIVATE")
    private TaskScope taskScope;
    @Schema(description = "할일 완료일", example = "2024-10-11 15:21:00")
    private LocalDateTime taskCompletionDate;
    @Schema(description = "할일 등록자", example = "demo@gmail.com")
    private String createdBy;
    @Schema(description = "할일 등록일", example = "2024-10-11 15:21:00")
    private LocalDateTime createdAt;
    @Schema(description = "할일 수정자", example = "demo@gmail.com")
    private String modifiedBy;
    @Schema(description = "할일 수정일", example = "2024-10-11 15:21:00")
    private LocalDateTime modifiedAt;

    @Schema(description = "할일 담당자 목록", example = "[demo@gmail.com]")
    private List<TaskAssigneeDto> taskAssignees = new ArrayList<>();

    public TaskDetailResponse(Long taskId, String tripName, String taskTitle, String taskFilePath, TaskStatus taskStatus, LocalDateTime taskDDay, TaskScope taskScope, LocalDateTime taskCompletionDate, String createdBy, LocalDateTime createdAt, String modifiedBy, LocalDateTime modifiedAt) {
        this.taskId = taskId;
        this.tripName = tripName;
        this.taskTitle = taskTitle;
        this.taskFilePath = taskFilePath;
        this.taskStatus = taskStatus;
        this.taskDDay = taskDDay;
        this.taskScope = taskScope;
        this.taskCompletionDate = taskCompletionDate;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
    }

}
