package com.omsprog.travel.repository;

import com.omsprog.travel.entity.jpa.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {

}
