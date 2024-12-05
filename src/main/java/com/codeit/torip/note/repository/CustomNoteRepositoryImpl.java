package com.codeit.torip.note.repository;

import com.codeit.torip.note.dto.NoteDetailDto;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        BooleanExpression condition = key.equals("TRAVEL") ? travel.id.eq(travelOrTaskId) : task.id.eq(travelOrTaskId);
        if (seq != 0) condition = condition.and(note.id.lt(seq));
        return factory.select(
                        Projections.constructor(NoteDetailDto.class,
                                note.id, travel.name, task.status, note.title, note.content,
                                note.lastcreatedUser.email, note.createdAt, note.lastUpdatedUser.email, note.updatedAt
                        )
                ).from(travel).join(travel.tasks, task)
                .join(task.notes, note)
                .join(note.lastcreatedUser, createdBy)
                .join(note.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(note.id.desc())
                .limit(PAGE_OFFSET)
                .fetch();
    }

    @Override
    public NoteDetailDto selectNoteDetail(long noteId) {
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        return factory.select(
                        Projections.constructor(NoteDetailDto.class,
                                note.id, travel.name, task.status, note.title, note.content,
                                note.lastcreatedUser.email, note.createdAt, note.lastUpdatedUser.email, note.updatedAt
                        )
                ).from(travel)
                .join(travel.tasks, task)
                .join(task.notes, note)
                .join(note.lastcreatedUser, createdBy)
                .join(note.lastUpdatedUser, modifiedBy)
                .where(note.id.eq(noteId))
                .fetchOne();
    }

}
