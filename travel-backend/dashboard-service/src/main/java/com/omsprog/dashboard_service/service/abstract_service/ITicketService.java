package com.omsprog.dashboard_service.service.abstract_service;

import com.omsprog.dashboard_service.dto.request.TicketRequest;
import com.omsprog.dashboard_service.dto.response.TicketResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID> {
    BigDecimal findPrice(Long flightId);
    Page<TicketResponse> readAll(Integer page, Integer size);
}