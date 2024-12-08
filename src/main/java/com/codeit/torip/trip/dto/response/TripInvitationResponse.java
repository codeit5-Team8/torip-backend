package com.codeit.torip.trip.dto.response;

import com.codeit.torip.trip.entity.TripInvitationStatus;
import com.codeit.torip.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class TripInvitationResponse {
    private String travelName;
    private UserResponse invitee;
    private TripInvitationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
