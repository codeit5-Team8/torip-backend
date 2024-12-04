package com.codeit.torip.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssigneeDto {

    @Schema(description = "할일 고유키", example = "1")
    private Long taskId;
    @Schema(description = "사용자 고유키", example = "1")
    private Long userId;
    @Schema(description = "담당자 이름", example = "홍길동")
    private String username;
    @Schema(description = "담당자 이메일", example = "demo@gmail.com")
    private String email;

}
