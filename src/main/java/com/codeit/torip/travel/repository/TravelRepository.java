package com.codeit.torip.travel.repository;

import com.codeit.torip.travel.entity.Travel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

    List<Travel> findAllByMembersUserIdAndIdGreaterThanOrderByIdAsc(Long ownerId, Long id, Pageable pageable);
}
