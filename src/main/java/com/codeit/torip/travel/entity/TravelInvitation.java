package com.codeit.torip.travel.entity;

import com.codeit.torip.common.entity.BaseEntity;
import com.codeit.torip.travel.dto.TravelInvitationResponse;
import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
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
    @JoinColumn(name = "invitee_id", nullable = false)
    private User invitee; // 여행 신청자 PK (FK)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelInvitationStatus status; // 여행 수락 / 거부

    public TravelInvitation(Travel travel, User invitee) {
        this.travel = Objects.requireNonNull(travel);
        this.invitee = Objects.requireNonNull(invitee);
        this.status = TravelInvitationStatus.Pending;
    }

    public TravelInvitationResponse toResponse() {
        return TravelInvitationResponse.builder()
                .travelName(travel.getName())
                .invitee(invitee.toResponse())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .status(status)
                .build();
    }

    public void accept() {
        travel.addMember(invitee);
        this.status = TravelInvitationStatus.Accepted;
    }
}
