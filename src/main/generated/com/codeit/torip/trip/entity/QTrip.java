package com.codeit.torip.trip.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrip is a Querydsl query type for Trip
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTrip extends EntityPathBase<Trip> {

    private static final long serialVersionUID = 1253766528L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrip trip = new QTrip("trip");

    public final com.codeit.torip.common.entity.QBaseUserEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.codeit.torip.user.entity.QUser lastcreatedUser;

    // inherited
    public final com.codeit.torip.user.entity.QUser lastUpdatedUser;

    public final ListPath<TripMember, QTripMember> members = this.<TripMember, QTripMember>createList("members", TripMember.class, QTripMember.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final com.codeit.torip.user.entity.QUser owner;

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final ListPath<com.codeit.torip.task.entity.Task, com.codeit.torip.task.entity.QTask> tasks = this.<com.codeit.torip.task.entity.Task, com.codeit.torip.task.entity.QTask>createList("tasks", com.codeit.torip.task.entity.Task.class, com.codeit.torip.task.entity.QTask.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QTrip(String variable) {
        this(Trip.class, forVariable(variable), INITS);
    }

    public QTrip(Path<? extends Trip> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrip(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrip(PathMetadata metadata, PathInits inits) {
        this(Trip.class, metadata, inits);
    }

    public QTrip(Class<? extends Trip> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.codeit.torip.common.entity.QBaseUserEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.lastcreatedUser = _super.lastcreatedUser;
        this.lastUpdatedUser = _super.lastUpdatedUser;
        this.owner = inits.isInitialized("owner") ? new com.codeit.torip.user.entity.QUser(forProperty("owner")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

