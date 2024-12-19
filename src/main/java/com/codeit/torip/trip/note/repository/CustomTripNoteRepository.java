package com.codeit.torip.trip.note.repository;

import com.codeit.torip.trip.note.dto.TripNoteDetailDto;
import com.codeit.torip.trip.note.dto.request.TripNoteListRequest;
import com.codeit.torip.trip.note.dto.response.TripNoteDetailResponse;

import java.util.List;
import java.util.Optional;

public interface CustomTripNoteRepository {

    List<TripNoteDetailDto> selectTripNoteDetailList(TripNoteListRequest tripNoteListRequest);

    Optional<TripNoteDetailResponse> selectTripNoteDetail(long tripNoteId);

    boolean isAuthorizedToModify(long tripNoteId);

}
