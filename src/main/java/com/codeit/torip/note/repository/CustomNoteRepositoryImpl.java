package com.codeit.torip.note.repository;

import com.codeit.torip.note.dto.NoteDetailDto;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Note.PAGE_OFFSET;
import static com.codeit.torip.note.entity.QNote.note;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.travel.entity.QTravel.travel;

@RequiredArgsConstructor
public class CustomNoteRepositoryImpl implements CustomNoteRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<NoteDetailDto> selectNoteDetailList(String key, long travelOrTaskId, long seq) {
        var createBy = new QUser("createBy");
        var modifiedBy = new QUser("modifiedBy");
        return factory.select(
                        Projections.constructor(NoteDetailDto.class,
                                note.id, travel.name, task.status, note.title, note.content,
                                note.link, note.createBy.email, note.createdAt, note.modifiedBy.email, note.updatedAt
                        )
                ).from(travel).join(travel.tasks, task)
                .join(task.notes, note)
                .join(note.createBy, createBy)
                .join(note.modifiedBy, modifiedBy)
                .where(
                        (key.equals("TRAVEL") ? task.id.eq(travelOrTaskId) : travel.id.eq(travelOrTaskId)).and(note.id.lt(seq)))
                .orderBy(note.id.desc())
                .limit(PAGE_OFFSET)
                .fetch();
    }

    @Override
    public NoteDetailDto selectNoteDetail(long noteId) {
        var createBy = new QUser("createBy");
        var modifiedBy = new QUser("modifiedBy");
        return factory.select(
                        Projections.constructor(NoteDetailDto.class,
                                note.id, travel.name, task.status, note.title, note.content,
                                note.link, note.createBy.email, note.createdAt, note.modifiedBy.email, note.updatedAt
                        )
                ).from(travel)
                .join(travel.tasks, task)
                .join(task.notes, note)
                .join(note.createBy, createBy)
                .join(note.modifiedBy, modifiedBy)
                .where(note.id.eq(noteId)).fetchOne();
    }

}
