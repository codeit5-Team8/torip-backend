package com.codeit.torip.task.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCompletionResponse {


    @Schema(description = "여행 고유키", example = "1")
    private Long tripId;
    @Schema(description = "할일 고유키", example = "1")
    private Long taskId;

}
