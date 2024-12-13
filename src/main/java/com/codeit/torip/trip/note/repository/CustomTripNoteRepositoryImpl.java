package com.codeit.torip.trip.note.repository;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.trip.note.dto.request.TripNoteListRequest;
import com.codeit.torip.trip.note.dto.response.TripNoteDetailResponse;
import com.codeit.torip.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.codeit.torip.common.contant.ToripConstants.Note.PAGE_SIZE;
import static com.codeit.torip.trip.entity.QTrip.trip;
import static com.codeit.torip.trip.entity.QTripMember.tripMember;
import static com.codeit.torip.trip.note.entity.QTripNote.tripNote;

@RequiredArgsConstructor
public class CustomTripNoteRepositoryImpl implements CustomTripNoteRepository {

    private final JPAQueryFactory factory;

    @Override
    public List<TripNoteDetailResponse> selectTripNoteDetailList(TripNoteListRequest tripNoteListRequest) {
        var owner = new QUser("owner");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        var condition = trip.id.eq(tripNoteListRequest.getTripId())
                .and(tripNote.id.lt(tripNoteListRequest.getSeq()));
        condition = condition.and(getCommonCondition());
        // 노트 목록 불러오기
        return factory.select(
                        Projections.constructor(TripNoteDetailResponse.class,
                                tripNote.id, trip.name, tripNote.title, tripNote.content,
                                tripNote.lastcreatedUser.username, tripNote.createdAt,
                                tripNote.lastUpdatedUser.username, tripNote.updatedAt
                        )
                ).from(trip)
                .join(trip.owner, owner)
                .join(trip.notes, tripNote)
                .join(tripNote.lastcreatedUser, createdBy)
                .join(tripNote.lastUpdatedUser, modifiedBy)
                .where(condition)
                .orderBy(tripNote.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    @Override
    public TripNoteDetailResponse selectTripNoteDetail(long tripNoteId) {
        var owner = new QUser("owner");
        var member = new QUser("tripMember");
        var createdBy = new QUser("createdBy");
        var modifiedBy = new QUser("modifiedBy");
        // 쿼리 조건 생성
        var condition = tripNote.id.eq(tripNoteId);
        condition = condition.and(getCommonCondition());
        // 노트 상세 불러오기
        return factory.select(
                        Projections.constructor(TripNoteDetailResponse.class,
                                tripNote.id, trip.name, tripNote.title, tripNote.content,
                                tripNote.lastcreatedUser.username, tripNote.createdAt,
                                tripNote.lastUpdatedUser.username, tripNote.updatedAt
                        )
                ).from(trip)
                .join(trip.owner, owner)
                .join(trip.notes, tripNote)
                .join(trip.members, tripMember)
                .join(tripMember.user, member)
                .join(tripNote.lastcreatedUser, createdBy)
                .join(tripNote.lastUpdatedUser, modifiedBy)
                .where(condition)
                .fetchOne();
    }

    @Override
    public boolean isAuthorizedToModify(long tripNoteId) {
        var owner = new QUser("owner");
        var createdBy = new QUser("createdBy");
        // 쿼리 조건 생성
        var email = AuthUtil.getEmail();
        var condition = tripNote.id.eq(tripNoteId);
        condition = condition.and(trip.owner.email.eq(email).or(tripNote.lastcreatedUser.email.eq(email)));
        // 수정 가능 여부 판단
        return factory.selectOne()
                .from(tripNote)
                .join(tripNote.trip, trip)
                .join(trip.owner, owner)
                .join(tripNote.lastcreatedUser, createdBy)
                .where(condition)
                .fetchFirst() != null;
    }

    private BooleanExpression getCommonCondition() {
        var email = AuthUtil.getEmail();
        return trip.owner.email.eq(email).or(tripMember.user.email.eq(email));
    }

}
