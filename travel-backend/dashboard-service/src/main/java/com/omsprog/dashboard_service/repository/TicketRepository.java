package com.omsprog.dashboard_service.repository;

import com.omsprog.dashboard_service.entity.jpa.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
    @EntityGraph(attributePaths = {"flight"})
    Page<TicketEntity> findAll(Pageable pageable);
    Optional<TicketEntity> findByTourId(Long id);
}