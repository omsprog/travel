package com.omsprog.travel.service.abstract_service;

import com.omsprog.travel.dto.request.TourRequest;
import com.omsprog.travel.dto.response.TourResponse;
import com.omsprog.travel.dto.response.TourSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourRequest, TourResponse, Long> {
    Page<TourSummaryResponse> readAll(Integer page, Integer size);
    void removeTicket(Long tourId, UUID ticketId);
    UUID addTicket(Long flightId, Long tourId);
    void removeReservation(Long tourId, UUID reservationId);
    UUID addReservation(Long tourId, Long hotelId, Integer totalDays);
}
