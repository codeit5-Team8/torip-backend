package com.codeit.torip.travel.dto;

import com.codeit.torip.travel.entity.TravelInvitationStatus;
import com.codeit.torip.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class TravelInvitationResponse {
    private String travelName;
    private UserResponse inviter;
    private UserResponse invitee;
    private TravelInvitationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
