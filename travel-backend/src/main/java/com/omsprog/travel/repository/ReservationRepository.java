package com.omsprog.travel.repository;

import com.omsprog.travel.entity.jpa.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    @EntityGraph(attributePaths = {"hotel"})
    Page<ReservationEntity> findAll(Pageable pageable);
}
