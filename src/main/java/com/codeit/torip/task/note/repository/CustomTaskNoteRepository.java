package com.codeit.torip.task.note.repository;

import com.codeit.torip.task.note.dto.NoteDetailDto;
import com.codeit.torip.task.note.dto.request.TaskNoteListRequest;
import com.codeit.torip.task.note.dto.response.TaskNoteDeletedResponse;
import com.codeit.torip.task.note.dto.response.TaskNoteDetailResponse;
import com.codeit.torip.trip.note.dto.request.TripNoteListRequest;

import java.util.List;
import java.util.Optional;

public interface CustomTaskNoteRepository {

    List<NoteDetailDto> selectTaskNoteDetailList(TaskNoteListRequest taskNoteListRequest);

    Optional<TaskNoteDetailResponse> selectTaskNoteDetail(long taskNoteId);

    boolean isAuthorizedToModify(long taskNoteId);

    TaskNoteDeletedResponse deletedTaskNote(long taskNoteId);

    List<NoteDetailDto> selectTaskNoteDetailListFromTripId(TripNoteListRequest tripNoteListRequest);

}
