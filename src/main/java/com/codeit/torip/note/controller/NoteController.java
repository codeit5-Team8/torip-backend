package com.codeit.torip.note.controller;

import com.codeit.torip.common.dto.CommonResponse;
import com.codeit.torip.note.dto.request.NoteListRequest;
import com.codeit.torip.note.dto.request.NoteRequest;
import com.codeit.torip.note.dto.response.NoteDetailResponse;
import com.codeit.torip.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/torip/note")
@Tag(name = "Note", description = "노트 관련 API")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @Operation(summary = "노트 등록 API", description = "할일에 대한 노트를 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> registerNote(@RequestBody NoteRequest noteRequest) {
        var noteId = noteService.registerNode(noteRequest);
        return new CommonResponse<Long>().success(noteId);
    }

    @GetMapping
    @Operation(summary = "노트 목록 조회 API", description = "할일에 대한 노트 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<List<NoteDetailResponse>> getNoteList(@ModelAttribute NoteListRequest noteListRequest) {
        var noteDetailDtoList = noteService.getNoteList(noteListRequest);
        return new CommonResponse<List<NoteDetailResponse>>().success(noteDetailDtoList);
    }

    @GetMapping("/{noteId}")
    @Operation(summary = "노트 상세 조회 API", description = "할일에 대한 노트 상세 조회를 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<NoteDetailResponse> getNoteDetail(@PathVariable(name = "noteId") long noteId) {
        var noteDetail = noteService.getNoteDetail(noteId);
        return new CommonResponse<NoteDetailResponse>().success(noteDetail);
    }

    @PutMapping
    @Operation(summary = "노트 수정 API", description = "할일에 대한 노트를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> modifyNote(@RequestBody NoteRequest noteRequest) {
        var noteId = noteService.modifyNote(noteRequest);
        return new CommonResponse<Long>().success(noteId);
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "노트 삭제 API", description = "할일에 대한 노트를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<?> deleteNote(@PathVariable("noteId") long noteId) {
        noteService.deleteNote(noteId);
        return new CommonResponse<>().success(null);
    }

}
