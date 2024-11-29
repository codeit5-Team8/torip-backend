package com.codeit.torip.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTaskDto {

    @NotBlank
    private long travelId;
    @NotBlank
    private String taskTitle;
    private String filePath;
    @NotBlank
    private String taskStatus;
    private LocalDateTime taskDDay;
    @NotBlank
    private String scope;
    private boolean completion = false;
    private List<String> assigneeList;

}
