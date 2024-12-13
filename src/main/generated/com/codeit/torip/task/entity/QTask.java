package com.codeit.torip.task.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTask is a Querydsl query type for Task
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTask extends EntityPathBase<Task> {

    private static final long serialVersionUID = 2101569600L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTask task = new QTask("task");

    public final com.codeit.torip.common.entity.QBaseUserEntity _super;

    public final ListPath<TaskAssignee, QTaskAssignee> assignees = this.<TaskAssignee, QTaskAssignee>createList("assignees", TaskAssignee.class, QTaskAssignee.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> completionDate = createDateTime("completionDate", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.codeit.torip.user.entity.QUser lastcreatedUser;

    // inherited
    public final com.codeit.torip.user.entity.QUser lastUpdatedUser;

    public final ListPath<com.codeit.torip.task.note.entity.TaskNote, com.codeit.torip.task.note.entity.QTaskNote> notes = this.<com.codeit.torip.task.note.entity.TaskNote, com.codeit.torip.task.note.entity.QTaskNote>createList("notes", com.codeit.torip.task.note.entity.TaskNote.class, com.codeit.torip.task.note.entity.QTaskNote.class, PathInits.DIRECT2);

    public final EnumPath<TaskScope> scope = createEnum("scope", TaskScope.class);

    public final EnumPath<TripStatus> status = createEnum("status", TripStatus.class);

    public final DateTimePath<java.time.LocalDateTime> taskDDay = createDateTime("taskDDay", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final com.codeit.torip.trip.entity.QTrip trip;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QTask(String variable) {
        this(Task.class, forVariable(variable), INITS);
    }

    public QTask(Path<? extends Task> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTask(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTask(PathMetadata metadata, PathInits inits) {
        this(Task.class, metadata, inits);
    }

    public QTask(Class<? extends Task> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.codeit.torip.common.entity.QBaseUserEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.lastcreatedUser = _super.lastcreatedUser;
        this.lastUpdatedUser = _super.lastUpdatedUser;
        this.trip = inits.isInitialized("trip") ? new com.codeit.torip.trip.entity.QTrip(forProperty("trip"), inits.get("trip")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

