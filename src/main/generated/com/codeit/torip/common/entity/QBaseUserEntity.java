package com.codeit.torip.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBaseUserEntity is a Querydsl query type for BaseUserEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseUserEntity extends EntityPathBase<BaseUserEntity> {

    private static final long serialVersionUID = 1250996192L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBaseUserEntity baseUserEntity = new QBaseUserEntity("baseUserEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.codeit.torip.user.entity.QUser lastCreatedUser;

    public final com.codeit.torip.user.entity.QUser lastUpdatedUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QBaseUserEntity(String variable) {
        this(BaseUserEntity.class, forVariable(variable), INITS);
    }

    public QBaseUserEntity(Path<? extends BaseUserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBaseUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBaseUserEntity(PathMetadata metadata, PathInits inits) {
        this(BaseUserEntity.class, metadata, inits);
    }

    public QBaseUserEntity(Class<? extends BaseUserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lastCreatedUser = inits.isInitialized("lastCreatedUser") ? new com.codeit.torip.user.entity.QUser(forProperty("lastCreatedUser")) : null;
        this.lastUpdatedUser = inits.isInitialized("lastUpdatedUser") ? new com.codeit.torip.user.entity.QUser(forProperty("lastUpdatedUser")) : null;
    }

}

