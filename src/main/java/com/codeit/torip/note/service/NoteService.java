package com.codeit.torip.note.service;

import com.codeit.torip.note.dto.NoteDetailDto;
import com.codeit.torip.note.dto.NoteDto;
import com.codeit.torip.note.entity.Note;
import com.codeit.torip.note.repository.NoteRepository;
import com.codeit.torip.task.repository.TaskRepository;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Note.PAGE_OFFSET;
import static com.codeit.torip.note.entity.QNote.note;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.travel.entity.QTravel.travel;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NoteService {

    private final JPAQueryFactory factory;

    private final NoteRepository noteRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void registerNode(NoteDto noteDto) {
        var taskEntity = taskRepository.findById(noteDto.getTaskId()).orElseThrow(() -> new RuntimeException("할일이 존재하지 않습니다"));
        var noteEntity = Note.from(noteDto);
        noteEntity.setTask(taskEntity);
        noteRepository.save(noteEntity);
    }

    public NoteDetailDto getNoteDetail(long noteId) {
        QUser createBy = new QUser("createBy");
        QUser modifiedBy = new QUser("modifiedBy");
        return factory.select(
                        Projections.constructor(NoteDetailDto.class,
                                note.id, note.seq, travel.name, task.status, note.title, note.content,
                                note.link, note.createBy.email, note.createdAt, note.modifiedBy.email, note.updatedAt
                        )
                ).from(travel)
                .join(travel.tasks, task)
                .join(task.notes, note)
                .join(note.createBy, createBy)
                .join(note.modifiedBy, modifiedBy)
                .where(note.id.eq(noteId)).fetchOne();
    }

    public List<NoteDetailDto> getNoteList(long taskId, long seq) {
        QUser createBy = new QUser("createBy");
        QUser modifiedBy = new QUser("modifiedBy");
        return factory.select(
                        Projections.constructor(NoteDetailDto.class,
                                note.id, note.seq, travel.name, task.status, note.title, note.content,
                                note.link, note.createBy.email, note.createdAt, note.modifiedBy.email, note.updatedAt
                        )
                ).from(travel).join(travel.tasks, task)
                .join(task.notes, note)
                .join(note.createBy, createBy)
                .join(note.modifiedBy, modifiedBy)
                .where(task.id.eq(taskId).and(note.seq.between(seq, seq + PAGE_OFFSET))).fetch();
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
