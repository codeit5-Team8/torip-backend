package com.codeit.torip.task.controller;

import com.codeit.torip.common.dto.ResponseDto;
import com.codeit.torip.task.dto.TaskDetailDto;
import com.codeit.torip.task.dto.TaskDto;
import com.codeit.torip.task.dto.TaskProceedStatusDto;
import com.codeit.torip.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseDto<Long> registerTask(@RequestBody TaskDto taskDto) {
        var taskId = taskService.registerTask(taskDto);
        return new ResponseDto<Long>().success(taskId);
    }

    @GetMapping
    @Operation(summary = "할일 목록 조회 API", description = "여행에 대한 할일 목록을 조회합니다",
            parameters = {
                    @Parameter(
                            name = "travelId",
                            description = "여행 고유키",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "seq",
                            description = "현재 페이지에서 가장 작은 할일 고유키 [ 최초 조회시 0으로 요청 ]",
                            required = true,
                            example = "0"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public ResponseDto<List<TaskDetailDto>> getTaskList(@RequestParam(name = "travelId") long travelId, @RequestParam(name = "seq") long seq) {
        var taskDetailDtoList = taskService.getTaskList(travelId, seq);
        return new ResponseDto<List<TaskDetailDto>>().success(taskDetailDtoList);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "할일 상세 조회 API", description = "여행에 대한 할일을 상세 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public ResponseDto<TaskDetailDto> getTaskDetail(@PathVariable("taskId") long taskId) {
        var taskDetailDto = taskService.getTaskDetail(taskId);
        return new ResponseDto<TaskDetailDto>().success(taskDetailDto);
    }

    @PutMapping
    @Operation(summary = "할일 수정 API", description = "여행에 대한 할일을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public ResponseDto<Long> modifyTask(@RequestBody TaskDto taskDto) {
        var result = taskService.modifyTask(taskDto);
        return new ResponseDto<Long>().success(result);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "할일 삭제 API", description = "여행에 대한 할일을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public ResponseDto<Long> deleteTask(@PathVariable("taskId") long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseDto<Long>().success(taskId);
    }

    @GetMapping("/progress")
    @Operation(summary = "할일 완료도 조회 API", description = "여행에 대한 할일 완료도를 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public ResponseDto<TaskProceedStatusDto> getProgressStatus() {
        var taskProceedStatusDto = taskService.getProgressStatus();
        return new ResponseDto<TaskProceedStatusDto>().success(taskProceedStatusDto);
    }

}
