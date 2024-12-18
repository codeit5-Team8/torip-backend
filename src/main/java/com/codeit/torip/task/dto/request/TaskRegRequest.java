package com.codeit.torip.task.dto.request;

import com.codeit.torip.task.entity.TaskScope;
import com.codeit.torip.task.entity.TaskStatus;
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
public class TaskRegRequest {

    @NotNull(message = "여행 고유키는 필수 값입니다.")
    @Schema(description = "여행 고유키", example = "1")
    private Long tripId;
    @NotBlank(message = "할일 제목은 필수 값입니다.")
    @Schema(description = "할일 제목", example = "1")
    private String taskTitle;
    @Schema(description = "할일 파일 경로", example = "/home/image/sample.jpg", nullable = true)
    private String taskFilePath;
    @NotNull(message = "할일 여행 단계는 필수 값입니다.")
    @Schema(description = "할일 여행 단계", example = "BEFORE_TRIP / DURING_TRIP / AFTER_TRIP")
    private TaskStatus taskStatus;
    @NotNull(message = "할일 D-Day 는 필수 값입니다.")
    @Schema(description = "할일 D-Day", example = "2024-10-11 15:21:00")
    private LocalDateTime taskDDay;
    @NotNull(message = "할일 공유 범위는 필수 값입니다.")
    @Schema(description = "할일 공유 범위", example = "PUBLIC / PRIVATE")
    private TaskScope taskScope;
    @Schema(description = "할일 완료일", example = "2024-10-11 15:21:00", nullable = true)
    private LocalDateTime taskCompletionDate;
    @Schema(description = "할일 담당자 목록", example = "[demo@gmail.com]")
    private Set<String> taskAssignees = new HashSet<>();

}
