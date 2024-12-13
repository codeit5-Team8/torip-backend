package com.codeit.torip.task.note.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaskNote is a Querydsl query type for TaskNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTaskNote extends EntityPathBase<TaskNote> {

    private static final long serialVersionUID = -1458240138L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaskNote taskNote = new QTaskNote("taskNote");

    public final com.codeit.torip.common.entity.QBaseUserEntity _super;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.codeit.torip.user.entity.QUser lastcreatedUser;

    // inherited
    public final com.codeit.torip.user.entity.QUser lastUpdatedUser;

    public final com.codeit.torip.task.entity.QTask task;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QTaskNote(String variable) {
        this(TaskNote.class, forVariable(variable), INITS);
    }

    public QTaskNote(Path<? extends TaskNote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTaskNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTaskNote(PathMetadata metadata, PathInits inits) {
        this(TaskNote.class, metadata, inits);
    }

    public QTaskNote(Class<? extends TaskNote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.codeit.torip.common.entity.QBaseUserEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.lastcreatedUser = _super.lastcreatedUser;
        this.lastUpdatedUser = _super.lastUpdatedUser;
        this.task = inits.isInitialized("task") ? new com.codeit.torip.task.entity.QTask(forProperty("task"), inits.get("task")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

