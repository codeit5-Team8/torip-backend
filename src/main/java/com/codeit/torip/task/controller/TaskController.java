package com.codeit.torip.task.controller;

import com.codeit.torip.common.dto.ResponseDto;
import com.codeit.torip.task.dto.TaskDto;
import com.codeit.torip.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/torip/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "할일 등록 API", description = "여행에 대한 할일을 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto registerTask(@RequestBody TaskDto taskDto) {
        taskService.registerTask(taskDto);
        return ResponseDto.success(null);
    }

    @GetMapping
    @Operation(summary = "할일 목록 조회 API", description = "여행에 대한 할일 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto getTaskList(@RequestParam(name = "travelId") Long travelId, @RequestParam(name = "seq") Long seq) {
        return ResponseDto.success(taskService.getTaskList(travelId, seq));
    }

    @GetMapping("/{id}")
    @Operation(summary = "할일 상세 조회 API", description = "여행에 대한 할일을 상세 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto getTaskDetail(@PathVariable("id") Long taskId) {
        return ResponseDto.success(taskService.getTaskDetail(taskId));
    }

    @PutMapping
    @Operation(summary = "할일 수정 API", description = "여행에 대한 할일을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto modifyTask(@RequestBody TaskDto taskDto) {
        taskService.modifyTask(taskDto);
        return ResponseDto.success(null);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "할일 수정 API", description = "여행에 대한 할일을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseDto.success(null);
    }

    @GetMapping("/progress")
    public ResponseDto getProgressStatus() {
        return ResponseDto.success(taskService.getProgressStatus());
    }

}
