package com.codeit.torip.task.note.repository;

import com.codeit.torip.task.note.dto.request.TaskNoteListRequest;
import com.codeit.torip.task.note.dto.response.TaskNoteDeletedResponse;
import com.codeit.torip.task.note.dto.response.TaskNoteDetailResponse;

import java.util.List;

public interface CustomTaskNoteRepository {

    List<TaskNoteDetailResponse> selectTaskNoteDetailList(TaskNoteListRequest taskNoteListRequest);

    TaskNoteDetailResponse selectTaskNoteDetail(long taskNoteId);

    boolean isAuthorizedToModify(long taskNoteId);
    
    TaskNoteDeletedResponse deletedTaskNote(long taskNoteId);

}
