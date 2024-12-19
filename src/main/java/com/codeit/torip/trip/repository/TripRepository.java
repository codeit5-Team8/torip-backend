package com.codeit.torip.trip.repository;

import com.codeit.torip.trip.entity.Trip;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findAllByMembersUserIdAndIdGreaterThanOrderByIdAsc(Long ownerId, Long id, Pageable pageable);

    Optional<Trip> findTripById(Long id);

    @EntityGraph(attributePaths = "members")
    @Query("SELECT t FROM Trip t WHERE t.id = :tripId")
    Optional<Trip> findByIdWithMembers(@Param("tripId") Long tripId);

}
