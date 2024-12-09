package com.codeit.torip.trip.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateTripRequest {
    String name;
    LocalDate startDate;
    LocalDate endDate;
}
