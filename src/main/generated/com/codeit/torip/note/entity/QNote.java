package com.codeit.torip.note.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNote is a Querydsl query type for Note
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNote extends EntityPathBase<Note> {

    private static final long serialVersionUID = 1755390362L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNote note = new QNote("note");

    public final com.codeit.torip.common.entity.QBaseUserEntity _super;

    public final StringPath content = createString("content");

    // inherited
    public final com.codeit.torip.user.entity.QUser createBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.codeit.torip.user.entity.QUser lastcreatedUser;

    // inherited
    public final com.codeit.torip.user.entity.QUser lastUpdatedUser;

    public final StringPath link = createString("link");

    // inherited
    public final com.codeit.torip.user.entity.QUser modifiedBy;

    public final com.codeit.torip.task.entity.QTask task;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QNote(String variable) {
        this(Note.class, forVariable(variable), INITS);
    }

    public QNote(Path<? extends Note> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNote(PathMetadata metadata, PathInits inits) {
        this(Note.class, metadata, inits);
    }

    public QNote(Class<? extends Note> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.codeit.torip.common.entity.QBaseUserEntity(type, metadata, inits);
        this.createBy = _super.createBy;
        this.createdAt = _super.createdAt;
        this.lastcreatedUser = _super.lastcreatedUser;
        this.lastUpdatedUser = _super.lastUpdatedUser;
        this.modifiedBy = _super.modifiedBy;
        this.task = inits.isInitialized("task") ? new com.codeit.torip.task.entity.QTask(forProperty("task"), inits.get("task")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

