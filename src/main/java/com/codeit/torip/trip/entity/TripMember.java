package com.codeit.torip.trip.entity;

import com.codeit.torip.common.entity.BaseEntity;
import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "trip_member")
public class TripMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripMemberRole role;

    public TripMember(Trip trip, User user, TripMemberRole role) {
        this.trip = Objects.requireNonNull(trip);
        this.user = Objects.requireNonNull(user);
        this.role = Objects.requireNonNull(role);
    }
}