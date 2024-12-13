package com.codeit.torip.trip.note.repository;

import com.codeit.torip.trip.note.entity.TripNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripNoteRepository extends JpaRepository<TripNote, Long>, CustomTripNoteRepository {
}
