package com.codeit.torip.travel.entity;

import com.codeit.torip.common.entity.BaseEntity;
import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "travel_invitation")
public class TravelInvitation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inviter_id", nullable = false)
    private User inviter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_id", nullable = false)
    private User invitee; // 여행 신청자 PK (FK)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelInvitationStatus status; // 여행 수락 / 거부

    public TravelInvitation(Travel travel, User inviter, User invitee) {
        this.travel = Objects.requireNonNull(travel);
        this.inviter = Objects.requireNonNull(inviter);
        this.invitee = Objects.requireNonNull(invitee);
        this.status = TravelInvitationStatus.Pending;
    }
}
