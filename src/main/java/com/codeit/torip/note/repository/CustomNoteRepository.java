package com.codeit.torip.note.repository;

import com.codeit.torip.note.dto.response.NoteDetailResponse;

import java.util.List;

public interface CustomNoteRepository {

    List<NoteDetailResponse> selectNoteDetailList(String key, long tripOrTaskId, long seq);

    NoteDetailResponse selectNoteDetail(long noteId);

}
