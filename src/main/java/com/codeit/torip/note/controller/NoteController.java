package com.codeit.torip.note.controller;

import com.codeit.torip.common.dto.ResponseDto;
import com.codeit.torip.note.dto.NoteDetailDto;
import com.codeit.torip.note.dto.NoteDto;
import com.codeit.torip.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotatedElementUtils;
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
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<Long> registerNote(@RequestBody NoteDto noteDto) {
        var noteId = noteService.registerNode(noteDto);
        return new ResponseDto<Long>().success(noteId);
    }

    @GetMapping
    @Operation(summary = "노트 목록 조회 API", description = "할일에 대한 노트 목록을 조회합니다",
            parameters = {
                    @Parameter(
                            name = "key",
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
                            description = "현재 페이지에서 가장 작은 노트 고유키 [ 최초 조회시 0으로 요청 ]",
                            required = true,
                            example = "0"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<List<NoteDetailDto>> getNoteList(
            @RequestParam("key") String key, @RequestParam("id") long id, @RequestParam("seq") long seq
    ) {
        var noteDetailDtoList = noteService.getNoteList(key, id, seq);
        return new ResponseDto<List<NoteDetailDto>>().success(noteDetailDtoList);
    }

    @GetMapping("/{noteId}")
    @Operation(summary = "노트 상세 조회 API", description = "할일에 대한 노트 상세 조회를 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<NoteDetailDto> getNoteDetail(@PathVariable(name = "noteId") long noteId) {
        var noteDetail = noteService.getNoteDetail(noteId);
        return new ResponseDto<NoteDetailDto>().success(noteDetail);
    }

    @PutMapping
    @Operation(summary = "노트 수정 API", description = "할일에 대한 노트를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<Long> modifyNote(@RequestBody NoteDto noteDto) {
        var noteId = noteService.modifyNote(noteDto);
        return new ResponseDto<Long>().success(noteId);
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "노트 삭제 API", description = "할일에 대한 노트를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<?> deleteNote(@PathVariable("noteId") long noteId) {
        noteService.deleteNote(noteId);
        return new ResponseDto<>().success(null);
    }

}
