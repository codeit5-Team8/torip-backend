package com.codeit.torip.travel.repository;

import com.codeit.torip.travel.entity.TravelInvitation;
import com.codeit.torip.travel.entity.TravelInvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelInvitationRepository extends JpaRepository<TravelInvitation, Long> {

    List<TravelInvitation> findAllByTravelIdAndStatusOrderByCreatedAt(Long travelId, TravelInvitationStatus status);

    void deleteAllByTravelId(Long travelId);
}
