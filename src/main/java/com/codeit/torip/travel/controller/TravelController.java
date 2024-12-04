package com.codeit.torip.travel.controller;

import com.codeit.torip.common.dto.ResponseDto;
import com.codeit.torip.travel.dto.CreateTravelRequest;
import com.codeit.torip.travel.dto.UpdateTravelRequest;
import com.codeit.torip.travel.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Travel", description = "여행 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/torip/travel")
@RestController
public class TravelController {

    private final TravelService travelService;

    @PostMapping
    @Operation(summary = "여행 생성 API", description = "여행을 생성합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto createTravel(@RequestBody CreateTravelRequest createTravelRequest) {
        // 여행 생성 로직
        return ResponseDto.success(travelService.createTravel(createTravelRequest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "여행 조회 API", description = "여행을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto getTravel(@PathVariable Long id) {
        // 여행 조회 로직
        return ResponseDto.success(travelService.getTravel(id));
    }

    @GetMapping("/list")
    @Operation(summary = "여행 목록 조회 API", description = "여행 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto getTravelList(@RequestParam Long lastSeenId) {
        // 여행 목록 조회 로직
        return ResponseDto.success(travelService.getTravelList(lastSeenId));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "여행 수정 API", description = "여행을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto updateTravel(@PathVariable Long id, @RequestBody UpdateTravelRequest updateTravelRequest) {
        // 여행 수정 로직
        return ResponseDto.success(travelService.updateTravel(id, updateTravelRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "여행 삭제 API", description = "여행을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto deleteTravel(@PathVariable Long id) {
        // 여행 삭제 로직
        travelService.deleteTravel(id);

        return ResponseDto.success(null);
    }

    @PostMapping("/{id}/request")
    @Operation(summary = "여행 참가 요청 API", description = "여행에 참가를 요청합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto requestTravelParticipation(@PathVariable Long id, @RequestBody Long inviterId) {
        // 여행 참가 로직
        return ResponseDto.success(travelService.requestTravelParticipation(id, inviterId));
    }

    @PostMapping("request/{id}/accept")
    @Operation(summary = "여행 참가 수락 API", description = "여행 참가를 수락합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto acceptTravelParticipation(@PathVariable Long id) {
        // 여행 참가 수락 로직
        return ResponseDto.success(travelService.acceptTravelParticipation(id));
    }

    @GetMapping("/{id}/request")
    @Operation(summary = "여행 참가 요청 조회 API", description = "여행 참가 요청을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto getTravelInvitations(@PathVariable Long id) {
        // 여행 참가 요청 조회 로직
        return ResponseDto.success(travelService.getTravelInvitations(id));
    }

    @GetMapping("/{id}/members")
    @Operation(summary = "여행 참가자 조회 API", description = "여행 참가자를 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto getTravelMembers(@PathVariable Long id) {
        // 여행 참가자 조회 로직
        return ResponseDto.success(travelService.getTravelMembers(id));
    }
}
