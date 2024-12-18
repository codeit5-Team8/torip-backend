package com.codeit.torip.trip.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTripMember is a Querydsl query type for TripMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTripMember extends EntityPathBase<TripMember> {

    private static final long serialVersionUID = 708070458L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTripMember tripMember = new QTripMember("tripMember");

    public final com.codeit.torip.common.entity.QBaseEntity _super = new com.codeit.torip.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<TripMemberRole> role = createEnum("role", TripMemberRole.class);

    public final QTrip trip;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.codeit.torip.user.entity.QUser user;

    public QTripMember(String variable) {
        this(TripMember.class, forVariable(variable), INITS);
    }

    public QTripMember(Path<? extends TripMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTripMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTripMember(PathMetadata metadata, PathInits inits) {
        this(TripMember.class, metadata, inits);
    }

    public QTripMember(Class<? extends TripMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trip = inits.isInitialized("trip") ? new QTrip(forProperty("trip"), inits.get("trip")) : null;
        this.user = inits.isInitialized("user") ? new com.codeit.torip.user.entity.QUser(forProperty("user")) : null;
    }

}

