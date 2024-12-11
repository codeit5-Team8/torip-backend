package com.codeit.torip.trip.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTripInvitation is a Querydsl query type for TripInvitation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTripInvitation extends EntityPathBase<TripInvitation> {

    private static final long serialVersionUID = -1179162311L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTripInvitation tripInvitation = new QTripInvitation("tripInvitation");

    public final com.codeit.torip.common.entity.QBaseEntity _super = new com.codeit.torip.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.codeit.torip.user.entity.QUser invitee;

    public final EnumPath<TripInvitationStatus> status = createEnum("status", TripInvitationStatus.class);

    public final QTrip trip;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTripInvitation(String variable) {
        this(TripInvitation.class, forVariable(variable), INITS);
    }

    public QTripInvitation(Path<? extends TripInvitation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTripInvitation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTripInvitation(PathMetadata metadata, PathInits inits) {
        this(TripInvitation.class, metadata, inits);
    }

    public QTripInvitation(Class<? extends TripInvitation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.invitee = inits.isInitialized("invitee") ? new com.codeit.torip.user.entity.QUser(forProperty("invitee")) : null;
        this.trip = inits.isInitialized("trip") ? new QTrip(forProperty("trip"), inits.get("trip")) : null;
    }

}

