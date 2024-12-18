package com.codeit.torip.task.dto.request;

import com.codeit.torip.task.entity.TaskScope;
import com.codeit.torip.task.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskListRequest {

    @Schema(description = "여행 고유키", example = "1")
    private Long tripId = 0L;
    @Schema(description = "현재 페이지에서 가장 작은 할일 고유키", example = "1", defaultValue = "0")
    private Long taskSeq = 0L;
    @Schema(description = "할일 여행 단계", example = "NULL / BEFORE_TRIP / DURING_TRIP / AFTER_TRIP", nullable = true)
    private TaskStatus taskStatus;
    @Schema(description = "할일 공유 범위", example = "NULL / PUBLIC / PRIVATE", nullable = true)
    private TaskScope taskScope;
    @Schema(description = "전체 조회 FLAG", example = "ture / false", defaultValue = "false")
    private Boolean all = false;

}
