package com.omsprog.dashboard_service.service.abstract_service;

import com.omsprog.dashboard_service.dto.request.TourRequest;
import com.omsprog.dashboard_service.dto.response.TourResponse;
import com.omsprog.dashboard_service.dto.response.TourSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourResponse, Long> {
    Page<TourSummaryResponse> readAll(Integer page, Integer size);
    void removeTicket(Long tourId, UUID ticketId);
    UUID addTicket(Long flightId, Long tourId);
    void removeReservation(Long tourId, UUID reservationId);
    UUID addReservation(Long tourId, Long hotelId, Integer totalDays);
}
