package com.codeit.torip.task.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaskAssignee is a Querydsl query type for TaskAssignee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTaskAssignee extends EntityPathBase<TaskAssignee> {

    private static final long serialVersionUID = 1447402031L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaskAssignee taskAssignee = new QTaskAssignee("taskAssignee");

    public final com.codeit.torip.common.entity.QBaseUserEntity _super;

    public final com.codeit.torip.user.entity.QUser assignee;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // inherited
    public final com.codeit.torip.user.entity.QUser lastcreatedUser;

    // inherited
    public final com.codeit.torip.user.entity.QUser lastUpdatedUser;

    public final QTask task;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QTaskAssignee(String variable) {
        this(TaskAssignee.class, forVariable(variable), INITS);
    }

    public QTaskAssignee(Path<? extends TaskAssignee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTaskAssignee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTaskAssignee(PathMetadata metadata, PathInits inits) {
        this(TaskAssignee.class, metadata, inits);
    }

    public QTaskAssignee(Class<? extends TaskAssignee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.codeit.torip.common.entity.QBaseUserEntity(type, metadata, inits);
        this.assignee = inits.isInitialized("assignee") ? new com.codeit.torip.user.entity.QUser(forProperty("assignee")) : null;
        this.createdAt = _super.createdAt;
        this.lastcreatedUser = _super.lastcreatedUser;
        this.lastUpdatedUser = _super.lastUpdatedUser;
        this.task = inits.isInitialized("task") ? new QTask(forProperty("task"), inits.get("task")) : null;
        this.updatedAt = _super.updatedAt;
    }

}

