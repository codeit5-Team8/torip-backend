package com.codeit.torip.common.entity;

import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseUserEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_user_id", nullable = false)
    protected User lastUpdatedUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_created_user_id", nullable = false)
    protected User lastcreatedUser;
}
