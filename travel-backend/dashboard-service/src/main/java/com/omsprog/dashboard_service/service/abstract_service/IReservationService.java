package com.omsprog.dashboard_service.service.abstract_service;

import com.omsprog.dashboard_service.dto.request.ReservationRequest;
import com.omsprog.dashboard_service.dto.response.ReservationResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID>{
    BigDecimal findPrice(Long flightId);
    Page<ReservationResponse> readAll(Integer page, Integer size);
}