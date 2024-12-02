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
    public void registerNode(NoteDto noteDto) {
        var taskEntity = taskRepository.findById(noteDto.getTaskId())
                .orElseThrow(() -> new RuntimeException("할일이 존재하지 않습니다"));
        var noteEntity = Note.from(noteDto);
        noteEntity.setTask(taskEntity);
        noteRepository.save(noteEntity);
    }

    public List<NoteDetailDto> getNoteList(String key, long id, long seq) {
        return noteRepository.selectNoteDetailList(key,id,seq);
    }

    public NoteDetailDto getNoteDetail(long noteId) {
        return noteRepository.selectNoteDetail(noteId);
    }

    @Transactional
    public void modifyNote(NoteDto noteDto) {
        Note noteEntity = noteRepository.findById(noteDto.getNoteId())
                .orElseThrow(() -> new RuntimeException("노트 정보가 존재하지 않습니다"));
        noteEntity.modifyTo(noteDto);
        noteRepository.save(noteEntity);
    }

    @Transactional
    public void deleteNote(long noteId) {
        noteRepository.deleteById(noteId);
    }

}
