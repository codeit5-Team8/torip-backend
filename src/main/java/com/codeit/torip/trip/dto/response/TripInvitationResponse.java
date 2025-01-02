package com.codeit.torip.trip.dto.response;

import com.codeit.torip.trip.entity.TripInvitationStatus;
import com.codeit.torip.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class TripInvitationResponse {
    @Schema(description = "여행 초대 id", example = "1")
    private Long id;
    @Schema(description = "여행 id", example = "1")
    private Long tripId;
    @Schema(description = "여행 여행 이름", example = "제주도 여행")
    private String tripName;
    @Schema(description = "초대한 사람", example = """
            {
            "id": 1,
            "username": "test",
            "email": "test@test.com"
            }
            """)
    private UserResponse invitee;
    @Schema(description = "애행 초대 상황", example = "ACCEPTED, PENDING, REJECTED")
    private TripInvitationStatus status;
    @Schema(description = "초대를 한 시간", example = "2021-10-01T00:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "초대를 마지막으로 변경한 시간", example = "2021-10-01T00:00:00")
    private LocalDateTime updatedAt;
}
