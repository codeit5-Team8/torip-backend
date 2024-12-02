package com.codeit.torip.travel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTravelInvitation is a Querydsl query type for TravelInvitation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTravelInvitation extends EntityPathBase<TravelInvitation> {

    private static final long serialVersionUID = 294810851L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTravelInvitation travelInvitation = new QTravelInvitation("travelInvitation");

    public final com.codeit.torip.common.entity.QBaseEntity _super;

    // inherited
    public final com.codeit.torip.user.entity.QUser createBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.codeit.torip.user.entity.QUser invitee;

    public final com.codeit.torip.user.entity.QUser inviter;

    // inherited
    public final com.codeit.torip.user.entity.QUser modifiedBy;

    public final EnumPath<TravelInvitationStatus> status = createEnum("status", TravelInvitationStatus.class);

    public final QTravel travel;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QTravelInvitation(String variable) {
        this(TravelInvitation.class, forVariable(variable), INITS);
    }

    public QTravelInvitation(Path<? extends TravelInvitation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTravelInvitation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTravelInvitation(PathMetadata metadata, PathInits inits) {
        this(TravelInvitation.class, metadata, inits);
    }

    public QTravelInvitation(Class<? extends TravelInvitation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.codeit.torip.common.entity.QBaseEntity(type, metadata, inits);
        this.createBy = _super.createBy;
        this.createdAt = _super.createdAt;
        this.invitee = inits.isInitialized("invitee") ? new com.codeit.torip.user.entity.QUser(forProperty("invitee")) : null;
        this.inviter = inits.isInitialized("inviter") ? new com.codeit.torip.user.entity.QUser(forProperty("inviter")) : null;
        this.modifiedBy = _super.modifiedBy;
        this.travel = inits.isInitialized("travel") ? new QTravel(forProperty("travel"), inits.get("travel")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

