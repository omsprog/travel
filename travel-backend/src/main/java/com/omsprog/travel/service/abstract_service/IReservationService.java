package com.omsprog.travel.service.abstract_service;

import com.omsprog.travel.dto.request.ReservationRequest;
import com.omsprog.travel.dto.response.ReservationResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID>{
    BigDecimal findPrice(Long flyId);
}