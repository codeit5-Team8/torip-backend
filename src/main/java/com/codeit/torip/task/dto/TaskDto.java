package com.codeit.torip.task.dto;

import com.codeit.torip.task.entity.TaskScope;
import com.codeit.torip.task.entity.TravelStatus;
import jakarta.validation.constraints.NotBlank;
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
public class TaskDto {

    @NotBlank
    private long travelId;
    private long taskId;
    @NotBlank
    private String taskTitle;
    private String filePath;
    @NotBlank
    private TravelStatus travelStatus;
    private LocalDateTime taskDDay;
    @NotBlank
    private TaskScope scope;
    private LocalDateTime completionDate;
    private Set<String> assignees = new HashSet<>();

}
