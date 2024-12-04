package com.codeit.torip.travel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTravel is a Querydsl query type for Travel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTravel extends EntityPathBase<Travel> {

    private static final long serialVersionUID = 1515629994L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTravel travel = new QTravel("travel");

    public final com.codeit.torip.common.entity.QBaseEntity _super;

    // inherited
    public final com.codeit.torip.user.entity.QUser createBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Integer> dDay = createNumber("dDay", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<TravelMember, QTravelMember> members = this.<TravelMember, QTravelMember>createList("members", TravelMember.class, QTravelMember.class, PathInits.DIRECT2);

    // inherited
    public final com.codeit.torip.user.entity.QUser modifiedBy;

    public final StringPath name = createString("name");

    public final com.codeit.torip.user.entity.QUser owner;

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final ListPath<com.codeit.torip.task.entity.Task, com.codeit.torip.task.entity.QTask> tasks = this.<com.codeit.torip.task.entity.Task, com.codeit.torip.task.entity.QTask>createList("tasks", com.codeit.torip.task.entity.Task.class, com.codeit.torip.task.entity.QTask.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QTravel(String variable) {
        this(Travel.class, forVariable(variable), INITS);
    }

    public QTravel(Path<? extends Travel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTravel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTravel(PathMetadata metadata, PathInits inits) {
        this(Travel.class, metadata, inits);
    }

    public QTravel(Class<? extends Travel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.codeit.torip.common.entity.QBaseEntity(type, metadata, inits);
        this.createBy = _super.createBy;
        this.createdAt = _super.createdAt;
        this.modifiedBy = _super.modifiedBy;
        this.owner = inits.isInitialized("owner") ? new com.codeit.torip.user.entity.QUser(forProperty("owner")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

