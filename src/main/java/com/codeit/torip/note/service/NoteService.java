package com.codeit.torip.note.service;

import com.codeit.torip.note.dto.NoteDetailDto;
import com.codeit.torip.note.dto.NoteDto;
import com.codeit.torip.note.entity.Note;
import com.codeit.torip.note.repository.NoteRepository;
import com.codeit.torip.task.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NoteService {

    private final NoteRepository noteRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public Long registerNode(NoteDto noteDto) {
        // 할일 조회
        var taskEntity = taskRepository.findById(noteDto.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("할일이 존재하지 않습니다"));
        var noteEntity = Note.from(noteDto);
        noteEntity.setTask(taskEntity);
        // 노트 등록
        var result = noteRepository.save(noteEntity);
        return result.getId();
    }

    public List<NoteDetailDto> getNoteList(String key, long travelOrTaskId, long seq) {
        // 노트 목록 조회
        return noteRepository.selectNoteDetailList(key, travelOrTaskId, seq);
    }

    public NoteDetailDto getNoteDetail(long noteId) {
        // 노트 상세 조회
        return noteRepository.selectNoteDetail(noteId);
    }

    @Transactional
    public Long modifyNote(NoteDto noteDto) {
        // 노트 조회
        var noteEntity = noteRepository.findById(noteDto.getNoteId())
                .orElseThrow(() -> new IllegalArgumentException("노트 정보가 존재하지 않습니다"));
        // 노트 수정
        noteEntity.modifyTo(noteDto);
        var result = noteRepository.save(noteEntity);
        return result.getId();
    }

    @Transactional
    public void deleteNote(long noteId) {
        // 노트 삭제
        noteRepository.deleteById(noteId);
    }

}
