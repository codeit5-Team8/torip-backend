package com.codeit.torip.task.dto;

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

    private int personalTask;
    private int personalCompletionTask;
    private int commonTask;
    private int commonCompletionTask;
    private int totalTask;
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
