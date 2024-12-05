package com.codeit.torip.travel.controller;

import com.codeit.torip.common.dto.ResponseDto;
import com.codeit.torip.travel.dto.*;
import com.codeit.torip.travel.entity.Travel;
import com.codeit.torip.travel.service.TravelService;
import com.codeit.torip.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseDto<TravelResponse> createTravel(@RequestBody CreateTravelRequest createTravelRequest) {
        var travelResponse = travelService.createTravel(createTravelRequest);
        // 여행 생성 로직
        return new ResponseDto<TravelResponse>().success(travelResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "여행 조회 API", description = "여행을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<TravelResponse> getTravel(@PathVariable Long id) {
        var travelResponse = travelService.getTravel(id);
        // 여행 조회 로직
        return new ResponseDto<TravelResponse>().success(travelResponse);
    }

    @GetMapping("/list")
    @Operation(summary = "여행 목록 조회 API", description = "여행 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<PageCollectionResponse<TravelResponse>> getTravelList(@RequestParam Long lastSeenId) {
        var travelResponseList = travelService.getTravelList(lastSeenId);
        // 여행 목록 조회 로직
        return new ResponseDto<PageCollectionResponse<TravelResponse>>()
                .success(travelResponseList);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "여행 수정 API", description = "여행을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<TravelResponse> updateTravel(@PathVariable Long id, @RequestBody UpdateTravelRequest updateTravelRequest) {
        var travelResponse = travelService.updateTravel(id, updateTravelRequest);
        // 여행 수정 로직
        return new ResponseDto<TravelResponse>().success(travelResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "여행 삭제 API", description = "여행을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<?> deleteTravel(@PathVariable Long id) {
        // 여행 삭제 로직
        travelService.deleteTravel(id);
        return new ResponseDto<>().success(null);
    }

    @PostMapping("/{id}/request")
    @Operation(summary = "여행 참가 요청 API", description = "여행에 참가를 요청합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<TravelInvitationResponse> requestTravelParticipation(@PathVariable Long id, @RequestBody Long inviterId) {
        var travelInvitationResponse = travelService.requestTravelParticipation(id, inviterId);
        // 여행 참가 로직
        return new ResponseDto<TravelInvitationResponse>().success(travelInvitationResponse);
    }

    @PostMapping("request/{id}/accept")
    @Operation(summary = "여행 참가 수락 API", description = "여행 참가를 수락합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<TravelInvitationResponse> acceptTravelParticipation(@PathVariable Long id) {
        var travelInvitationResponse = travelService.acceptTravelParticipation(id);
        // 여행 참가 수락 로직
        return new ResponseDto<TravelInvitationResponse>().success(travelInvitationResponse);
    }

    @GetMapping("/{id}/request")
    @Operation(summary = "여행 참가 요청 조회 API", description = "여행 참가 요청을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<List<TravelInvitationResponse>> getTravelInvitations(@PathVariable Long id) {
        var travelInvitationResponseList = travelService.getTravelInvitations(id);
        // 여행 참가 요청 조회 로직
        return new ResponseDto<List<TravelInvitationResponse>>().success(travelInvitationResponseList);
    }

    @GetMapping("/{id}/members")
    @Operation(summary = "여행 참가자 조회 API", description = "여행 참가자를 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public ResponseDto<List<UserResponse>> getTravelMembers(@PathVariable Long id) {
        var userResponseList = travelService.getTravelMembers(id);
        // 여행 참가자 조회 로직
        return new ResponseDto<List<UserResponse>>().success(userResponseList);
    }
}
