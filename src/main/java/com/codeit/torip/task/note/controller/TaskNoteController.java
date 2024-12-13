package com.codeit.torip.task.note.controller;

import com.codeit.torip.common.dto.CommonResponse;
import com.codeit.torip.task.note.dto.request.TaskNoteListRequest;
import com.codeit.torip.task.note.dto.request.TaskNoteModRequest;
import com.codeit.torip.task.note.dto.request.TaskNoteRegRequest;
import com.codeit.torip.task.note.dto.response.TaskNoteDetailResponse;
import com.codeit.torip.task.note.service.TaskNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/torip/task/note")
@Tag(name = "Note", description = "노트 관련 API")
public class TaskNoteController {

    private final TaskNoteService taskNoteService;

    @PostMapping
    @Operation(summary = "할일 노트 등록 API", description = "할일에 대한 노트를 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> registerTaskNote(@RequestBody TaskNoteRegRequest taskNoteRegRequest) {
        var taskNoteId = taskNoteService.registerTaskNote(taskNoteRegRequest);
        return new CommonResponse<Long>().success(taskNoteId);
    }

    @GetMapping
    @Operation(summary = "할일 노트 목록 조회 API", description = "할일에 대한 노트 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<List<TaskNoteDetailResponse>> getTaskNoteList(@ModelAttribute TaskNoteListRequest taskNoteListRequest) {
        var taskNoteDetailList = taskNoteService.getTaskNoteList(taskNoteListRequest);
        return new CommonResponse<List<TaskNoteDetailResponse>>().success(taskNoteDetailList);
    }

    @GetMapping("/{taskNoteId}")
    @Operation(summary = "할일 노트 상세 조회 API", description = "할일에 대한 노트 상세 조회를 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TaskNoteDetailResponse> getTaskNoteDetail(@PathVariable(name = "taskNoteId") long taskNoteId) {
        var taskNoteDetail = taskNoteService.getTaskNoteDetail(taskNoteId);
        return new CommonResponse<TaskNoteDetailResponse>().success(taskNoteDetail);
    }

    @PutMapping
    @Operation(summary = "할일 노트 수정 API", description = "할일에 대한 노트를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> modifyTaskNote(@RequestBody TaskNoteModRequest taskNoteModRequest) {
        var taskNoteId = taskNoteService.modifyTaskNote(taskNoteModRequest);
        return new CommonResponse<Long>().success(taskNoteId);
    }

    @DeleteMapping("/{taskNoteId}")
    @Operation(summary = "할일 노트 삭제 API", description = "할일에 대한 노트를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<?> deleteTaskNote(@PathVariable("taskNoteId") long taskNoteId) {
        taskNoteService.deleteTaskNote(taskNoteId);
        return new CommonResponse<>().success(null);
    }

}