package com.omsprog.dashboard_service.repository;

import com.omsprog.dashboard_service.dto.response.TourSummaryResponse;
import com.omsprog.dashboard_service.entity.jpa.TourEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
    @Query("SELECT new com.omsprog.travel.dto.response.TourSummaryResponse( " +
            "tr.id, tr.name, c.fullName, " +
            "COUNT(DISTINCT ti.id), COUNT(DISTINCT r.id)) " +
            "FROM tour tr " +
            "JOIN tr.customer c " +
            "JOIN tr.tickets ti " +
            "JOIN tr.reservations r " +
            "GROUP BY tr.id, tr.name, c.fullName")
    Page<TourSummaryResponse> findTourSummary(Pageable pageable);
}