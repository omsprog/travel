package com.omsprog.dashboard_service.repository;

import com.omsprog.dashboard_service.entity.jpa.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    // JPA Queries

    @Query("select f from flight f where f.price < :price")
    Set<FlightEntity> selectLessPrice(BigDecimal price);

    @Query("select f from flight f where f.price between :min and :max")
    Set<FlightEntity> selectBetweenPrice(BigDecimal min, BigDecimal max);

    @Query("select f from flight f where f.originName = :origin and f.destinationName = :destination")
    Set<FlightEntity> selectOriginDestination(String origin, String destination);

    @Query("select f from flight f join fetch f.tickets t where t.id = :id")
    Optional<FlightEntity> findByTicketId(UUID id);
}