package com.codeit.torip.travel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTravelMember is a Querydsl query type for TravelMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTravelMember extends EntityPathBase<TravelMember> {

    private static final long serialVersionUID = 32226532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTravelMember travelMember = new QTravelMember("travelMember");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<TravelMemberRole> role = createEnum("role", TravelMemberRole.class);

    public final QTravel travel;

    public final com.codeit.torip.user.entity.QUser user;

    public QTravelMember(String variable) {
        this(TravelMember.class, forVariable(variable), INITS);
    }

    public QTravelMember(Path<? extends TravelMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTravelMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTravelMember(PathMetadata metadata, PathInits inits) {
        this(TravelMember.class, metadata, inits);
    }

    public QTravelMember(Class<? extends TravelMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.travel = inits.isInitialized("travel") ? new QTravel(forProperty("travel"), inits.get("travel")) : null;
        this.user = inits.isInitialized("user") ? new com.codeit.torip.user.entity.QUser(forProperty("user")) : null;
    }

}

