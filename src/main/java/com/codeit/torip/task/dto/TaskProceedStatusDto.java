package com.codeit.torip.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskProceedStatusDto {

    @Schema(description = "개인 할일 수", example = "1")
    private int personalTask;
    @Schema(description = "개인 할일 완료 수", example = "1")
    private int personalCompletionTask;
    @Schema(description = "공통 할일 수", example = "1")
    private int commonTask;
    @Schema(description = "공통 할일 완료 수", example = "1")
    private int commonCompletionTask;
    @Schema(description = "전체 할일 수", example = "1")
    private int totalTask;
    @Schema(description = "전체 할일 완료 수", example = "1")
    private int totalCompletionTask;

    public void setPersonalTask(LocalDateTime completionDate) {
        if (completionDate != null) personalCompletionTask++;
        personalTask++;
        totalTask++;
    }

    public void setCommonTask(LocalDateTime completionDate) {
        if (completionDate != null) commonCompletionTask++;
        commonTask++;
        totalTask++;
    }
}
