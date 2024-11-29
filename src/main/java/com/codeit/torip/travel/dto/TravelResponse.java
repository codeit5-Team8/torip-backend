package com.codeit.torip.travel.dto;

import com.codeit.torip.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class TravelResponse {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserResponse owner;
    private LocalDateTime createdAt;
    private UserResponse lastUpdatedUser;
    private LocalDateTime updatedAt;
}
