package com.codeit.torip.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageCollectionResponse<T> {
    private Long lastSeenId;
    private List<T> content;
}
