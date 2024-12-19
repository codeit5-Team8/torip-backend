package com.codeit.torip.trip.note.repository;

import com.codeit.torip.trip.note.entity.TripNote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripNoteRepository extends JpaRepository<TripNote, Long>, CustomTripNoteRepository {

    @EntityGraph(attributePaths = "trip")
    @Query("SELECT tn FROM TripNote tn WHERE tn.id = :tripNoteId")
    Optional<TripNote> findByIdWithTrip(@Param("tripNoteId") Long tripNoteId);

}
