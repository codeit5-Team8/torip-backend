package com.codeit.torip.note.repository;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.note.dto.response.NoteDetailResponse;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Note.PAGE_SIZE;
import static com.codeit.torip.note.entity.QNote.note;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.task.entity.QTaskAssignee.taskAssignee;
import static com.codeit.torip.trip.entity.QTrip.trip;

@RequiredArgsConstructor
public class CustomNoteRepositoryImpl implements CustomNoteRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<NoteDetailResponse> selectNoteDetailList(String key, long travelOrTaskId, long seq) {
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        BooleanExpression condition = getCondition(assignee);
        condition = key.equals("TRAVEL") ? condition.and(trip.id.eq(travelOrTaskId))
                : condition.and(task.id.eq(travelOrTaskId));
        if (seq != 0) condition = condition.and(note.id.lt(seq));
        return factory.select(
                        Projections.constructor(NoteDetailResponse.class,
                                note.id, trip.name, task.status, note.title, note.content,
                                note.lastcreatedUser.email, note.createdAt, note.lastUpdatedUser.email, note.updatedAt
                        )
                ).from(trip).join(trip.tasks, task)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, assignee)
                .join(task.notes, note)
                .join(note.lastcreatedUser, createdBy)
                .join(note.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(note.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    @Override
    public NoteDetailResponse selectNoteDetail(long noteId) {
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        BooleanExpression condition = getCondition(assignee);
        condition.and(note.id.eq(noteId));
        return factory.select(
                        Projections.constructor(NoteDetailResponse.class,
                                note.id, trip.name, task.status, note.title, note.content,
                                note.lastcreatedUser.email, note.createdAt, note.lastUpdatedUser.email, note.updatedAt
                        )
                ).from(trip)
                .join(trip.tasks, task)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, assignee)
                .join(task.notes, note)
                .join(note.lastcreatedUser, createdBy)
                .join(note.lastUpdatedUser, modifiedBy)
                .where(condition)
                .fetchOne();
    }

    private BooleanExpression getCondition(QUser assignee) {
        return assignee.email.eq(AuthUtil.getEmail());
    }

}
