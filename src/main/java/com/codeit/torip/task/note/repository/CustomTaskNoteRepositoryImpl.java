package com.codeit.torip.task.note.repository;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.task.note.dto.request.TaskNoteListRequest;
import com.codeit.torip.task.note.dto.response.TaskNoteDeletedResponse;
import com.codeit.torip.task.note.dto.response.TaskNoteDetailResponse;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Note.PAGE_SIZE;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.task.entity.QTaskAssignee.taskAssignee;
import static com.codeit.torip.task.note.entity.QTaskNote.taskNote;
import static com.codeit.torip.trip.entity.QTrip.trip;

@RequiredArgsConstructor
public class CustomTaskNoteRepositoryImpl implements CustomTaskNoteRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<TaskNoteDetailResponse> selectTaskNoteDetailList(TaskNoteListRequest taskNoteListRequest) {
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        var taskId = taskNoteListRequest.getTaskId();
        var seq = taskNoteListRequest.getSeq();
        // 쿼리 조건 생성
        BooleanExpression condition = getCondition(assignee);
        condition = condition.and(task.id.eq(taskId));
        if (seq != 0) condition = condition.and(taskNote.id.lt(seq));
        return factory.select(
                        Projections.constructor(TaskNoteDetailResponse.class,
                                taskNote.id, trip.name, task.title, task.status, taskNote.title, taskNote.content,
                                taskNote.lastCreatedUser.username, taskNote.createdAt,
                                taskNote.lastUpdatedUser.username, taskNote.updatedAt
                        )
                ).from(trip).join(trip.tasks, task)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, assignee)
                .join(task.notes, taskNote)
                .join(taskNote.lastCreatedUser, createdBy)
                .join(taskNote.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(taskNote.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    @Override
    public TaskNoteDetailResponse selectTaskNoteDetail(long taskNoteId) {
        var assignee = new QUser("assignee");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        BooleanExpression condition = getCondition(assignee);
        condition.and(taskNote.id.eq(taskNoteId));
        return factory.select(
                        Projections.constructor(TaskNoteDetailResponse.class,
                                taskNote.id, trip.name, task.title, task.status, taskNote.title, taskNote.content,
                                taskNote.lastCreatedUser.username, taskNote.createdAt,
                                taskNote.lastUpdatedUser.username, taskNote.updatedAt
                        )
                ).from(trip)
                .join(trip.tasks, task)
                .join(task.assignees, taskAssignee)
                .join(taskAssignee.assignee, assignee)
                .join(task.notes, taskNote)
                .join(taskNote.lastCreatedUser, createdBy)
                .join(taskNote.lastUpdatedUser, modifiedBy)
                .where(condition)
                .fetchOne();
    }

    @Override
    public boolean isAuthorizedToModify(long taskNoteId) {
        var owner = new QUser("owner");
        var writer = new QUser("writer");
        var createdBy = new QUser("createdBy");
        // 쿼리 조건 생성
        var email = AuthUtil.getEmail();
        var condition = taskNote.id.eq(taskNoteId);
        condition = condition.and(owner.email.eq(email)
                .or(createdBy.email.eq(email)).or(writer.email.eq(email)));
        // 수정 가능 여부 판단
        return factory.selectOne()
                .from(taskNote)
                .join(taskNote.task, task)
                .join(task.lastCreatedUser, createdBy)
                .join(task.trip, trip)
                .join(trip.owner, owner)
                .join(taskNote.lastCreatedUser, writer)
                .where(condition)
                .fetchFirst() != null;
    }

    @Override
    public TaskNoteDeletedResponse deletedTaskNote(long taskNoteId) {
        // 쿼리 조건 생성
        BooleanExpression condition = taskNote.id.eq(taskNoteId);
        return factory.select(
                        Projections.constructor(TaskNoteDeletedResponse.class,
                                trip.id, taskNote.title, taskNote.content
                        )
                ).from(trip)
                .join(trip.tasks, task)
                .join(task.notes, taskNote)
                .where(condition)
                .fetchOne();
    }

    private BooleanExpression getCondition(QUser assignee) {
        return assignee.email.eq(AuthUtil.getEmail());
    }

}
