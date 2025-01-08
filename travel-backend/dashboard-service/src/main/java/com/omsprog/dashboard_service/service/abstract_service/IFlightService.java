package com.omsprog.dashboard_service.service.abstract_service;

import com.omsprog.dashboard_service.dto.request.FlightRequest;
import com.omsprog.dashboard_service.dto.response.FlightResponse;
import com.omsprog.dashboard_service.util.SortType;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface IFlightService extends CatalogService<FlightResponse> {
    Set<FlightResponse> readByOriginDestination(String origin, String destination);
    FlightResponse create(FlightRequest request);
    FlightResponse read(Long id);
    FlightResponse update(FlightRequest request, Long id);
    Page<FlightResponse> readAll(Integer page, Integer size, SortType sortType);
}
