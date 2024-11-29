package com.codeit.torip.travel.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateTravelRequest {
    String name;
    LocalDate startDate;
    LocalDate endDate;
}
