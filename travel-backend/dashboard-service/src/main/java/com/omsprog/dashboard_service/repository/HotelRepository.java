package com.omsprog.dashboard_service.repository;

import com.omsprog.dashboard_service.entity.jpa.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    Set<HotelEntity> findByPriceLessThan(BigDecimal price);
    Set<HotelEntity> findByPriceIsBetween(BigDecimal min, BigDecimal max);
    Set<HotelEntity> findByRatingGreaterThan(Integer rating);
    Optional<HotelEntity> findByName(String name);
}