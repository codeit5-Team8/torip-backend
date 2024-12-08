package com.codeit.torip.trip.repository;

import com.codeit.torip.trip.entity.Trip;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findAllByMembersUserIdAndIdGreaterThanOrderByIdAsc(Long ownerId, Long id, Pageable pageable);
}
