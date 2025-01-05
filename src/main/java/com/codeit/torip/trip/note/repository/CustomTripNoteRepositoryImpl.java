package com.codeit.torip.trip.note.repository;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.task.note.dto.NoteDetailDto;
import com.codeit.torip.trip.note.dto.request.TripNoteListRequest;
import com.codeit.torip.trip.note.dto.response.TripNoteDetailResponse;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.codeit.torip.common.contant.ToripConstants.Note.PAGE_SIZE;
import static com.codeit.torip.trip.entity.QTrip.trip;
import static com.codeit.torip.trip.entity.QTripMember.tripMember;
import static com.codeit.torip.trip.note.entity.QTripNote.tripNote;

@RequiredArgsConstructor
public class CustomTripNoteRepositoryImpl implements CustomTripNoteRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<NoteDetailDto> selectTripNoteDetailList(TripNoteListRequest tripNoteListRequest) {
        var member = new QUser("member");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        var owner = new QUser("owner");
        // 쿼리 조건 생성
        var seq = tripNoteListRequest.getTripNoteSeq();

        var condition = getCommonCondition();
        condition.and(trip.id.eq(tripNoteListRequest.getId()));
        if (seq != null && seq != 0) condition.and(tripNote.id.lt(seq));
        // 노트 목록 불러오기
        return factory.selectDistinct(
                        Projections.constructor(NoteDetailDto.class,
                                tripNote.id, trip.owner.id, tripNote.title, tripNote.content,
                                tripNote.lastCreatedUser.id,
                                tripNote.lastCreatedUser.username, tripNote.createdAt,
                                tripNote.lastUpdatedUser.username, tripNote.updatedAt
                        )
                ).from(trip)
                .join(trip.members, tripMember)
                .join(tripMember.user, member)
                .join(trip.notes, tripNote)
                .join(trip.owner, owner)
                .join(tripNote.lastCreatedUser, createdBy)
                .join(tripNote.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(tripNote.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    @Override
    public Optional<TripNoteDetailResponse> selectTripNoteDetail(long tripNoteId) {
        var tripUser = new QUser("tripUser");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        var owner = new QUser("owner");
        // 쿼리 조건 생성
        var condition = getCommonCondition();
        condition.and(tripNote.id.eq(tripNoteId));
        // 노트 상세 불러오기
        var tripNoteDetail = factory.selectDistinct(
                        Projections.constructor(TripNoteDetailResponse.class,
                                tripNote.id, trip.id, trip.owner.id, trip.name, tripNote.title, tripNote.content,
                                tripNote.lastCreatedUser.id,
                                tripNote.lastCreatedUser.username, tripNote.createdAt,
                                tripNote.lastUpdatedUser.username, tripNote.updatedAt
                        )
                ).from(trip)
                .join(trip.notes, tripNote)
                .join(trip.members, tripMember)
                .join(tripMember.user, tripUser)
                .join(trip.owner, owner)
                .join(tripNote.lastCreatedUser, createdBy)
                .join(tripNote.lastUpdatedUser, modifiedBy)
                .where(condition)
                .fetchOne();
        return Optional.ofNullable(tripNoteDetail);
    }

    @Override
    public boolean isAuthorizedToModify(long tripNoteId) {
        var owner = new QUser("owner");
        var createdBy = new QUser("createdBy");
        // 쿼리 조건 생성
        var email = AuthUtil.getEmail();
        var condition = new BooleanBuilder();
        condition.and(tripNote.id.eq(tripNoteId));
        condition.and(trip.owner.email.eq(email).or(tripNote.lastCreatedUser.email.eq(email)));
        // 수정 가능 여부 판단
        return factory.selectOne()
                .from(tripNote)
                .join(tripNote.trip, trip)
                .join(trip.owner, owner)
                .join(tripNote.lastCreatedUser, createdBy)
                .where(condition)
                .fetchFirst() != null;
    }

    private BooleanBuilder getCommonCondition() {
        var email = AuthUtil.getEmail();
        var condition = new BooleanBuilder();
        condition.and(tripMember.user.email.eq(email));
        return condition;
    }

}
