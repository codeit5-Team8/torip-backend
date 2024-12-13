package com.codeit.torip.task.dto.request;

import com.codeit.torip.task.entity.TaskScope;
import com.codeit.torip.task.entity.TripStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotNull
    @Schema(description = "여행 고유키", example = "1", nullable = false)
    private Long tripId;
    @Schema(description = "할일 고유키", example = "1", nullable = false)
    private Long taskId;
    @NotBlank
    @Schema(description = "할일 제목", example = "1", nullable = false)
    private String taskTitle;
    @Schema(description = "할일 파일 경로", example = "/home/image/sample.jpg")
    private String filePath;
    @NotNull
    @Schema(description = "할일 여행 단계", example = "BEFORE_TRIP / DURING_TRIP / AFTER_TRIP")
    private TripStatus tripStatus;
    @Schema(description = "할일 D-Day", example = "2024-10-11 15:21:00")
    private LocalDateTime taskDDay;
    @NotNull
    @Schema(description = "할일 공유 범위", example = "PUBLIC / PRIVATE")
    private TaskScope scope;
    @Schema(description = "할일 완료일", example = "2024-10-11 15:21:00")
    private LocalDateTime completionDate;
    @Schema(description = "할일 담당자 목록", example = "[demo@gmail.com]")
    private Set<String> assignees = new HashSet<>();

}
