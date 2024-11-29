package com.codeit.torip.travel.entity;

import com.codeit.torip.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "travel_member")
public class TravelMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유키 (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel; // 여행 PK (FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저 PK (FK)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelMemberRole role; // 권한
}