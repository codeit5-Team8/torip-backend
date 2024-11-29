package com.codeit.torip.travel.controller;

import com.codeit.torip.travel.dto.CreateTravelRequest;
import com.codeit.torip.travel.dto.TravelResponse;
import com.codeit.torip.travel.dto.UpdateTravelRequest;
import com.codeit.torip.travel.service.TravelService;
import com.codeit.torip.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Travel", description = "여행 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/travel")
@RestController
public class TravelController {

    private final TravelService travelService;

    @PostMapping
    public ResponseEntity<TravelResponse> createTravel(@RequestBody CreateTravelRequest createTravelRequest) {
        // 여행 생성 로직
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(travelService.createTravel(createTravelRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelResponse> getTravel(@PathVariable Long id) {
        // 여행 조회 로직
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelService.getTravel(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<TravelResponse>> getTravelList(@RequestParam Long lastSeenId) {
        // 여행 목록 조회 로직
        // TODO 임시로 전체 여행 조회로 해놓음
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelService.getTravelList());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TravelResponse> updateTravel(@PathVariable Long id, @RequestBody UpdateTravelRequest updateTravelRequest) {
        // 여행 수정 로직
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelService.updateTravel(id, updateTravelRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTravel(@PathVariable Long id) {
        // 여행 삭제 로직
        travelService.deleteTravel(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(null);
    }

    @PostMapping("/{id}/request")
    public void requestTravelParticipation(@PathVariable Long id, @RequestBody Long inviterId) {
        // 여행 참가 로직
        travelService.requestTravelParticipation(id, inviterId);
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<UserResponse>> getTravelMembers(@PathVariable Long id) {
        // 여행 참가자 조회 로직
        return ResponseEntity.status(HttpStatus.OK)
                .body(travelService.getTravelMembers(id));
    }
}
