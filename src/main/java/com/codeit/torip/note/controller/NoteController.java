package com.codeit.torip.note.controller;

import com.codeit.torip.common.dto.ResponseDto;
import com.codeit.torip.note.dto.NoteDto;
import com.codeit.torip.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/torip/note")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @Operation(summary = "할일 등록 API", description = "할일에 대한 노트를 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto registerNote(@RequestBody NoteDto noteDto) {
        noteService.registerNode(noteDto);
        return ResponseDto.success(null);
    }

    @GetMapping
    @Operation(summary = "할일 목록 조회 API", description = "할일에 대한 노트 목록을 조회합니다",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "여행/할일 필터링 구분",
                            required = true,
                            example = "TRAVEL / TASK"
                    ),
                    @Parameter(
                            name = "id",
                            description = "여행/할일 고유키",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "seq",
                            description = "현재 페이지에서 노트의 가장 작은 순번 [ 최초 조회시 0으로 요청 ]",
                            required = true,
                            example = "0"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto getNoteList(
            @RequestParam("key") String key, @RequestParam("id") long id, @RequestParam("seq") long seq
    ) {
        return ResponseDto.success(noteService.getNoteList(key, id, seq));
    }

    @GetMapping("/{id}")
    @Operation(summary = "할일 상세 조회 API", description = "할일에 대한 노트 상세 조회를 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto getNoteDetail(@PathVariable(name = "id") long noteId) {
        return ResponseDto.success(noteService.getNoteDetail(noteId));
    }

    @PutMapping
    @Operation(summary = "할일 수정 API", description = "할일에 대한 노트를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto modifyNote(@RequestBody NoteDto noteDto) {
        noteService.modifyNote(noteDto);
        return ResponseDto.success(null);
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "할일 수정 API", description = "할일에 대한 노트를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto deleteNote(@PathVariable("noteId") long noteId) {
        noteService.deleteNote(noteId);
        return ResponseDto.success(null);
    }

}