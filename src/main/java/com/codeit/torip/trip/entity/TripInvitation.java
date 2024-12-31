package com.codeit.torip.trip.entity;

import com.codeit.torip.common.entity.BaseEntity;
import com.codeit.torip.trip.dto.response.TripInvitationResponse;
import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "trip_invitation")
public class TripInvitation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitee_id", nullable = false)
    private User invitee; // 여행 신청자 PK (FK)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripInvitationStatus status; // 여행 수락 / 거부

    public TripInvitation(Trip trip, User invitee) {
        this.trip = Objects.requireNonNull(trip);
        this.invitee = Objects.requireNonNull(invitee);
        this.status = TripInvitationStatus.Pending;
    }

    public TripInvitationResponse toResponse() {
        return TripInvitationResponse.builder()
                .id(id)
                .tripId(trip.getId())
                .tripName(trip.getName())
                .invitee(invitee.toResponse())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .status(status)
                .build();
    }

    public void accept() {
        trip.addMember(invitee);
        this.status = TripInvitationStatus.Accepted;
    }

    public void reject() {
        this.status = TripInvitationStatus.Rejected;
    }

    public void reapply() {
        this.status = TripInvitationStatus.Pending;
    }

}
