package com.omsprog.travel.repository;

import com.omsprog.travel.entity.jpa.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
    Optional<TicketEntity> findByTourId(Long id);
}