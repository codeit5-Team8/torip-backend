package com.codeit.torip.task.controller;

import com.codeit.torip.common.dto.CommonResponse;
import com.codeit.torip.task.dto.request.TaskListRequest;
import com.codeit.torip.task.dto.request.TaskModRequest;
import com.codeit.torip.task.dto.request.TaskRegRequest;
import com.codeit.torip.task.dto.response.TaskDetailResponse;
import com.codeit.torip.task.dto.response.TaskProceedStatusResponse;
import com.codeit.torip.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/torip/task")
@Tag(name = "Task", description = "할일 관련 API")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "할일 등록 API", description = "여행에 대한 할일을 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> registerTask(@RequestBody @Valid TaskRegRequest taskRegRequest) {
        var taskId = taskService.registerTask(taskRegRequest);
        return new CommonResponse<Long>().success(taskId);
    }

    @GetMapping
    @Operation(summary = "할일 목록 조회 API", description = "여행에 대한 할일 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<List<TaskDetailResponse>> getTaskList(@ModelAttribute TaskListRequest taskListRequest) {
        var taskDetailDtoList = taskService.getTaskList(taskListRequest);
        return new CommonResponse<List<TaskDetailResponse>>().success(taskDetailDtoList);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "할일 상세 조회 API", description = "여행에 대한 할일을 상세 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TaskDetailResponse> getTaskDetail(@PathVariable("taskId") long taskId) {
        var taskDetailDto = taskService.getTaskDetail(taskId);
        return new CommonResponse<TaskDetailResponse>().success(taskDetailDto);
    }

    @PutMapping
    @Operation(summary = "할일 수정 API", description = "여행에 대한 할일을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> modifyTask(@RequestBody @Valid TaskModRequest taskModRequest) {
        var result = taskService.modifyTask(taskModRequest);
        return new CommonResponse<Long>().success(result);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "할일 삭제 API", description = "여행에 대한 할일을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> deleteTask(@PathVariable("taskId") long taskId) {
        taskService.deleteTask(taskId);
        return new CommonResponse<Long>().success(taskId);
    }

    @GetMapping("/progress")
    @Operation(summary = "할일 진행도 조회 API", description = "내 할일에 대한 진행도를 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TaskProceedStatusResponse> getProgressStatus() {
        var taskProceedStatusDto = taskService.getProgressStatus();
        return new CommonResponse<TaskProceedStatusResponse>().success(taskProceedStatusDto);
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "할일 완료 API", description = "할일 완료 처리",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<?> completeTask(@PathVariable("taskId") long taskId) {
        taskService.completeTask(taskId);
        return new CommonResponse<>().success(null);
    }

}
