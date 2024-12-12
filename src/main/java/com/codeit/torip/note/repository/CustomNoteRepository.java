package com.codeit.torip.note.repository;

import com.codeit.torip.note.dto.request.NoteListRequest;
import com.codeit.torip.note.dto.response.NoteDetailResponse;

import java.util.List;

public interface CustomNoteRepository {

    List<NoteDetailResponse> selectNoteDetailList(NoteListRequest noteListRequest);

    NoteDetailResponse selectNoteDetail(long noteId);

}
