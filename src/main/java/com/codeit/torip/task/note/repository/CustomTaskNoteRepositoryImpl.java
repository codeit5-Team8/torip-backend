package com.codeit.torip.task.note.repository;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.task.note.dto.NoteDetailDto;
import com.codeit.torip.task.note.dto.request.TaskNoteListRequest;
import com.codeit.torip.task.note.dto.response.TaskNoteDeletedResponse;
import com.codeit.torip.task.note.dto.response.TaskNoteDetailResponse;
import com.codeit.torip.trip.note.dto.request.TripNoteListRequest;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.codeit.torip.common.contant.ToripConstants.Note.PAGE_SIZE;
import static com.codeit.torip.task.entity.QTask.task;
import static com.codeit.torip.task.entity.TaskScope.PUBLIC;
import static com.codeit.torip.task.note.entity.QTaskNote.taskNote;
import static com.codeit.torip.trip.entity.QTrip.trip;
import static com.codeit.torip.trip.entity.QTripMember.tripMember;

@RequiredArgsConstructor
public class CustomTaskNoteRepositoryImpl implements CustomTaskNoteRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<NoteDetailDto> selectTaskNoteDetailList(TaskNoteListRequest taskNoteListRequest) {
        var member = new QUser("member");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        var owner = new QUser("owner");
        var taskId = taskNoteListRequest.getId();
        var seq = taskNoteListRequest.getNoteSeq();
        // 쿼리 조건 생성
        var condition = getCommonCondition();
        condition.and(task.id.eq(taskId));
        if (seq != null && seq != 0) condition.and(taskNote.id.lt(seq));
        condition.or(task.scope.eq(PUBLIC).and(task.id.eq(taskId)));
        if (seq != null && seq != 0) condition.and(taskNote.id.lt(seq));
        return factory.selectDistinct(
                        Projections.constructor(NoteDetailDto.class,
                                taskNote.id, trip.owner.id, task.taskStatus, task.title, taskNote.title, taskNote.content,
                                taskNote.lastCreatedUser.id, taskNote.lastCreatedUser.username, taskNote.createdAt,
                                taskNote.lastUpdatedUser.username, taskNote.updatedAt
                        )
                ).from(trip)
                .join(trip.members, tripMember)
                .join(tripMember.user, member)
                .join(trip.tasks, task)
                .join(task.notes, taskNote)
                .join(trip.owner, owner)
                .join(taskNote.lastCreatedUser, createdBy)
                .join(taskNote.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(taskNote.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    @Override
    public Optional<TaskNoteDetailResponse> selectTaskNoteDetail(long taskNoteId) {
        var member = new QUser("member");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        var owner = new QUser("owner");
        // 쿼리 조건 생성
        var condition = getCommonCondition();
        condition.and(taskNote.id.eq(taskNoteId));
        var taskNoteDetail = factory.selectDistinct(
                        Projections.constructor(TaskNoteDetailResponse.class,
                                taskNote.id, trip.owner.id, trip.name, task.taskStatus, task.title, taskNote.title, taskNote.content,
                                taskNote.lastCreatedUser.id, taskNote.lastCreatedUser.username, taskNote.createdAt,
                                taskNote.lastUpdatedUser.username, taskNote.updatedAt
                        )
                ).from(trip)
                .join(trip.members, tripMember)
                .join(tripMember.user, member)
                .join(trip.tasks, task)
                .join(task.notes, taskNote)
                .join(trip.owner, owner)
                .join(taskNote.lastCreatedUser, createdBy)
                .join(taskNote.lastUpdatedUser, modifiedBy)
                .where(condition)
                .fetchOne();
        return Optional.ofNullable(taskNoteDetail);
    }

    @Override
    public boolean isAuthorizedToModify(long taskNoteId) {
        var owner = new QUser("owner");
        var writer = new QUser("writer");
        var createdBy = new QUser("createdBy");
        // 쿼리 조건 생성
        var email = AuthUtil.getEmail();
        var condition = new BooleanBuilder();
        condition.and(taskNote.id.eq(taskNoteId));
        condition.and(owner.email.eq(email).or(createdBy.email.eq(email)).or(writer.email.eq(email)));
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

    @Override
    public List<NoteDetailDto> selectTaskNoteDetailListFromTripId(TripNoteListRequest tripNoteListRequest) {
        var member = new QUser("member");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        var owner = new QUser("owner");
        // 쿼리 조건 생성
        var condition = getCommonCondition();
        var tripId = tripNoteListRequest.getId();
        condition.and(trip.id.eq(tripId));
        var seq = tripNoteListRequest.getTaskNoteSeq();
        if (seq != null && seq != 0) condition.and(taskNote.id.lt(seq));
        condition.or(task.scope.eq(PUBLIC).and(trip.id.eq(tripId)));
        if (seq != null && seq != 0) condition.and(taskNote.id.lt(seq));
        return factory.selectDistinct(
                        Projections.constructor(NoteDetailDto.class,
                                taskNote.id, trip.owner.id, task.taskStatus, task.title, taskNote.title, taskNote.content,
                                taskNote.lastCreatedUser.id,
                                taskNote.lastCreatedUser.username, taskNote.createdAt,
                                taskNote.lastUpdatedUser.username, taskNote.updatedAt
                        )
                ).from(trip)
                .join(trip.members, tripMember)
                .join(tripMember.user, member)
                .join(trip.tasks, task)
                .join(task.notes, taskNote)
                .join(trip.owner, owner)
                .join(taskNote.lastCreatedUser, createdBy)
                .join(taskNote.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(taskNote.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    private BooleanBuilder getCommonCondition() {
        var email = AuthUtil.getEmail();
        var condition = new BooleanBuilder();
        return condition.and(tripMember.user.email.eq(email));
    }

}
