package com.omsprog.travel.repository;

import com.omsprog.travel.entity.jpa.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<TourEntity, Long> {

}