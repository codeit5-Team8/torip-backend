package com.codeit.torip.task.note.repository;

import com.codeit.torip.task.note.dto.TaskNoteDetailDto;
import com.codeit.torip.task.note.dto.request.TaskNoteListRequest;
import com.codeit.torip.task.note.dto.response.TaskNoteDeletedResponse;
import com.codeit.torip.task.note.dto.response.TaskNoteDetailResponse;
import com.codeit.torip.trip.note.dto.request.TripNoteListRequest;

import java.util.List;
import java.util.Optional;

public interface CustomTaskNoteRepository {

    List<TaskNoteDetailDto> selectTaskNoteDetailList(TaskNoteListRequest taskNoteListRequest);

    Optional<TaskNoteDetailResponse> selectTaskNoteDetail(long taskNoteId);

    boolean isAuthorizedToModify(long taskNoteId);

    TaskNoteDeletedResponse deletedTaskNote(long taskNoteId);

    List<TaskNoteDetailDto> selectTaskNoteDetailListFromTripId(TripNoteListRequest tripNoteListRequest);

}
