package com.codeit.torip.trip.note.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTripNote is a Querydsl query type for TripNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTripNote extends EntityPathBase<TripNote> {

    private static final long serialVersionUID = -1839457930L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTripNote tripNote = new QTripNote("tripNote");

    public final com.codeit.torip.common.entity.QBaseUserEntity _super;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.codeit.torip.user.entity.QUser lastCreatedUser;

    // inherited
    public final com.codeit.torip.user.entity.QUser lastUpdatedUser;

    public final StringPath title = createString("title");

    public final com.codeit.torip.trip.entity.QTrip trip;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QTripNote(String variable) {
        this(TripNote.class, forVariable(variable), INITS);
    }

    public QTripNote(Path<? extends TripNote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTripNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTripNote(PathMetadata metadata, PathInits inits) {
        this(TripNote.class, metadata, inits);
    }

    public QTripNote(Class<? extends TripNote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.codeit.torip.common.entity.QBaseUserEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.lastCreatedUser = _super.lastCreatedUser;
        this.lastUpdatedUser = _super.lastUpdatedUser;
        this.trip = inits.isInitialized("trip") ? new com.codeit.torip.trip.entity.QTrip(forProperty("trip"), inits.get("trip")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

