package com.omsprog.dashboard_service.controller.web;

import com.omsprog.dashboard_service.dto.request.FlightRequest;
import com.omsprog.dashboard_service.dto.response.FlightResponse;
import com.omsprog.dashboard_service.service.abstract_service.IFlightService;
import com.omsprog.dashboard_service.util.SortType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path="flights")
@AllArgsConstructor
@Tag(name = "flight")
public class FlightController {
    private final IFlightService flightService;

    @GetMapping
    public ResponseEntity<Page<FlightResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType
    ) {
        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.flightService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<FlightResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.read(id));
    }

    @PostMapping()
    public ResponseEntity<FlightResponse> create(@Valid @RequestBody FlightRequest flightRequest) {
        return ResponseEntity.ok(flightService.create(flightRequest));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<FlightResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody FlightRequest flightRequest
    ) {
        return ResponseEntity.ok(flightService.update(flightRequest, id));
    }

    @GetMapping(path = "less_price")
    public ResponseEntity<Set<FlightResponse>> getLessPrice(
            @RequestParam BigDecimal price
    ) {
        var response = this.flightService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "between_price")
    public ResponseEntity<Set<FlightResponse>> getBetweenPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max
    ) {
        var response = this.flightService.readBetweenPrice(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "origin_destination")
    public ResponseEntity<Set<FlightResponse>> getOriginDesination(
            @RequestParam String origin,
            @RequestParam String destination
    ) {
        var response = this.flightService.readByOriginDestination(origin, destination);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}