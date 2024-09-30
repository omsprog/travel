package com.omsprog.travel.service.abstract_service;

import com.omsprog.travel.dto.request.TicketRequest;
import com.omsprog.travel.dto.response.TicketResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID> {
    BigDecimal findPrice(Long flyId);
}