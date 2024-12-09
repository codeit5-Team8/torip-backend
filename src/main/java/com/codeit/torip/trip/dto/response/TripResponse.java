package com.codeit.torip.trip.dto.response;

import com.codeit.torip.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class TripResponse {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserResponse owner;
    private LocalDateTime createdAt;
    private UserResponse lastUpdatedUser;
    private LocalDateTime updatedAt;
}
