package com.codeit.torip.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageCollection<T> {
    private Long lastSeenId;
    private List<T> content;
}
