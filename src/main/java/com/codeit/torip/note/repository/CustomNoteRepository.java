package com.codeit.torip.note.repository;

import com.codeit.torip.note.dto.NoteDetailDto;

import java.util.List;

public interface CustomNoteRepository {

    List<NoteDetailDto> selectNoteDetailList(String key, long travelOrTaskId, long seq);

    NoteDetailDto selectNoteDetail(long noteId);

}
