package com.codeit.torip.trip.controller;

import com.codeit.torip.common.dto.CommonResponse;
import com.codeit.torip.trip.dto.PageCollection;
import com.codeit.torip.trip.dto.request.CreateTripRequest;
import com.codeit.torip.trip.dto.request.UpdateTripRequest;
import com.codeit.torip.trip.dto.response.TripInvitationResponse;
import com.codeit.torip.trip.dto.response.TripResponse;
import com.codeit.torip.trip.service.TripService;
import com.codeit.torip.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Trip", description = "여행 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/torip/trip")
@RestController
public class TripController {

    private final TripService tripService;

    @PostMapping
    @Operation(summary = "여행 생성 API", description = "여행을 생성합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<TripResponse> createTrip(@RequestBody CreateTripRequest createTripRequest) {
        var TripResponse = tripService.createTrip(createTripRequest);
        // 여행 생성 로직
        return new CommonResponse<TripResponse>().success(TripResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "여행 조회 API", description = "여행을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<TripResponse> getTrip(@PathVariable Long id) {
        var TripResponse = tripService.getTrip(id);
        // 여행 조회 로직
        return new CommonResponse<TripResponse>().success(TripResponse);
    }

    @GetMapping("/list")
    @Operation(summary = "여행 목록 조회 API", description = "여행 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<PageCollection<TripResponse>> getTripList(@RequestParam Long lastSeenId) {
        var TripResponseList = tripService.getTripList(lastSeenId);
        // 여행 목록 조회 로직
        return new CommonResponse<PageCollection<TripResponse>>()
                .success(TripResponseList);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "여행 수정 API", description = "여행을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<TripResponse> updateTrip(@PathVariable Long id, @RequestBody UpdateTripRequest updateTripRequest) {
        var TripResponse = tripService.updateTrip(id, updateTripRequest);
        // 여행 수정 로직
        return new CommonResponse<TripResponse>().success(TripResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "여행 삭제 API", description = "여행을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<?> deleteTrip(@PathVariable Long id) {
        // 여행 삭제 로직
        tripService.deleteTrip(id);
        return new CommonResponse<>().success("여행이 삭제되었습니다.");
    }

    @PostMapping("/{tripId}/request")
    @Operation(summary = "여행 참가 요청 API", description = "여행에 참가를 요청합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<TripInvitationResponse> requestTripParticipation(@PathVariable Long tripId) {
        var TripInvitationResponse = tripService.requestTripParticipation(tripId);
        // 여행 참가 로직
        return new CommonResponse<TripInvitationResponse>().success(TripInvitationResponse);
    }

    @PostMapping("request/{id}/accept")
    @Operation(summary = "여행 참가 수락 API", description = "여행 참가를 수락합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<TripInvitationResponse> acceptTripParticipation(@PathVariable Long id) {
        var TripInvitationResponse = tripService.acceptTripParticipation(id);
        // 여행 참가 수락 로직
        return new CommonResponse<TripInvitationResponse>().success(TripInvitationResponse);
    }

    @PostMapping("request/{id}/reject")
    @Operation(summary = "여행 참가 거절 API", description = "여행 참가를 거절합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<TripInvitationResponse> rejectTripParticipation(@PathVariable Long id) {
        var TripInvitationResponse = tripService.rejectTripParticipation(id);
        // 여행 참가 수락 로직
        return new CommonResponse<TripInvitationResponse>().success(TripInvitationResponse);
    }

    @GetMapping("/{id}/request")
    @Operation(summary = "여행 참가 요청 조회 API", description = "여행 참가 요청을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<List<TripInvitationResponse>> getTripInvitations(@PathVariable Long id) {
        var TripInvitationResponseList = tripService.getTripInvitations(id);
        // 여행 참가 요청 조회 로직
        return new CommonResponse<List<TripInvitationResponse>>().success(TripInvitationResponseList);
    }

    @GetMapping("/{id}/members")
    @Operation(summary = "여행 참가자 조회 API", description = "여행 참가자를 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<List<UserResponse>> getTripMembers(@PathVariable Long id) {
        var userResponseList = tripService.getTripMembers(id);
        // 여행 참가자 조회 로직
        return new CommonResponse<List<UserResponse>>().success(userResponseList);
    }
}
