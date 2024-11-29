package com.codeit.torip.travel.entity;

import com.codeit.torip.common.entity.BaseEntity;
import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Getter
@Table(name = "travel_member")
public class TravelMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelMemberRole role;

    public TravelMember(Travel travel, User user, TravelMemberRole role) {
        this.travel = Objects.requireNonNull(travel);
        this.user = Objects.requireNonNull(user);
        this.role = Objects.requireNonNull(role);
    }
}