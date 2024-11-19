package com.omsprog.travel.service.abstract_service;

import com.omsprog.travel.dto.request.FlightRequest;
import com.omsprog.travel.dto.response.FlightResponse;

import java.util.Set;

public interface IFlightService extends CatalogService<FlightResponse> {
    Set<FlightResponse> readByOriginDestination(String origin, String destination);
    FlightResponse create(FlightRequest request);
    FlightResponse read(Long id);
    FlightResponse update(FlightRequest request, Long id);
}
