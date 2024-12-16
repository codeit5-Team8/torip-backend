package com.codeit.torip.trip.repository;

import com.codeit.torip.trip.entity.TripInvitation;
import com.codeit.torip.trip.entity.TripInvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripInvitationRepository extends JpaRepository<TripInvitation, Long> {

    List<TripInvitation> findAllByTripIdAndStatusOrderByCreatedAt(Long tripId, TripInvitationStatus status);

    Boolean existsByInviteeIdAndTripId(Long tripId, Long inviteeId);

    void deleteAllByTripId(Long tripId);
}
