package com.codeit.torip.trip.note.controller;

import com.codeit.torip.common.dto.CommonResponse;
import com.codeit.torip.trip.note.dto.request.TripNoteListRequest;
import com.codeit.torip.trip.note.dto.request.TripNoteModRequest;
import com.codeit.torip.trip.note.dto.request.TripNoteRegRequest;
import com.codeit.torip.trip.note.dto.response.TripNoteDetailListResponse;
import com.codeit.torip.trip.note.dto.response.TripNoteDetailResponse;
import com.codeit.torip.trip.note.service.TripNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/torip/trip/note")
@Tag(name = "Trip Note", description = "여행 노트 관련 API")
public class TripNoteController {

    private final TripNoteService tripNoteService;

    @GetMapping
    @Operation(summary = "여행별 노트 모아보기 API", description = "여행에 대한 노트 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TripNoteDetailListResponse> getTripNoteList(@ModelAttribute @Valid TripNoteListRequest tripNoteListRequest) {
        var tripNoteDetailList = tripNoteService.getNoteList(tripNoteListRequest);
        return new CommonResponse<TripNoteDetailListResponse>().success(tripNoteDetailList);
    }

    @PostMapping
    @Operation(summary = "여행 노트 등록 API", description = "여행에 대한 노트를 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> registerTripNote(@RequestBody @Valid TripNoteRegRequest tripNoteRegRequest) {
        var tripNoteId = tripNoteService.registerNote(tripNoteRegRequest);
        return new CommonResponse<Long>().success(tripNoteId);
    }

    @GetMapping("/{noteId}")
    @Operation(summary = "여행 노트 상세 조회 API", description = "여행에 대한 노트 상세 조회를 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TripNoteDetailResponse> getTripNoteDetail(@PathVariable(name = "noteId") Long tripNoteId) {
        var tripNoteDetail = tripNoteService.getNoteDetail(tripNoteId);
        return new CommonResponse<TripNoteDetailResponse>().success(tripNoteDetail);
    }

    @PutMapping
    @Operation(summary = "여행 노트 수정 API", description = "여행에 대한 노트를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> modifyTripNote(@RequestBody @Valid TripNoteModRequest tripNoteModRequest) {
        var tripNoteId = tripNoteService.modifyNote(tripNoteModRequest);
        return new CommonResponse<Long>().success(tripNoteId);
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "여행 노트 삭제 API", description = "여행에 대한 노트를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<?> deleteTripNote(@PathVariable("noteId") Long tripNoteId) {
        tripNoteService.deleteNote(tripNoteId);
        return new CommonResponse<>().success(null);
    }

}
