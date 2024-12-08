package com.codeit.torip.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskModifyAssigneeDto {

    private long taskAssigneeId;
    private String email;

}
