package com.codeit.torip.task.dto;

import com.codeit.torip.task.entity.TaskScope;
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

    private TaskScope taskScope;
    private LocalDateTime taskCompletionDate;

}
