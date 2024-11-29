package com.codeit.torip.travel.repository;

import com.codeit.torip.travel.entity.TravelInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelInvitationRepository extends JpaRepository<TravelInvitation, Long> {
}
