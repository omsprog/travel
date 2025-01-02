package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.dto.request.FlightRequest;
import com.omsprog.travel.dto.response.FlightResponse;
import com.omsprog.travel.entity.jpa.FlightEntity;
import com.omsprog.travel.exception.RecordNotFoundException;
import com.omsprog.travel.repository.FlightRepository;
import com.omsprog.travel.service.abstract_service.IFlightService;
import com.omsprog.travel.util.SortType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional()
@Service
@Slf4j
@AllArgsConstructor // Creates the constructor for the dependency injection
public class FlightService implements IFlightService {

    private final FlightRepository flightRepository;

    @Override
    public Page<FlightResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;

        switch(sortType) {
            case NONE, LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        return this.flightRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
//    @Cacheable(value = CacheConstants.FLIGHT_CACHE_NAME)
    public Set<FlightResponse> readLessPrice(BigDecimal price) {
        return this.flightRepository.selectLessPrice(price)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
//    @Cacheable(value = CacheConstants.FLIGHT_CACHE_NAME)
    public Set<FlightResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return this.flightRepository.selectBetweenPrice(min, max)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
//    @Cacheable(value = CacheConstants.FLIGHT_CACHE_NAME)
    public Set<FlightResponse> readByOriginDestination(String origin, String destination) {
        return this.flightRepository.selectOriginDestination(origin, destination)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public FlightResponse create(FlightRequest request) {
        FlightEntity entityToBePersisted = FlightEntity.builder()
                .originLat(request.getOriginLat())
                .originLng(request.getOriginLng())
                .destinationLat(request.getDestinationLat())
                .destinationLng(request.getDestinationLng())
                .originName(request.getOriginName())
                .destinationName(request.getDestinationName())
                .price(request.getPrice())
                .aeroLine(request.getAeroLine())
                .build();
        return this.entityToResponse(this.flightRepository.save(entityToBePersisted));
    }

    @Override
    public FlightResponse read(Long id) {
        return this.entityToResponse(this.flightRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("flight")));
    }

    @Override
    public FlightResponse update(FlightRequest request, Long id) {
        var flightToUpdate = this.flightRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("flight"));

        flightToUpdate.setOriginLat(request.getOriginLat());
        flightToUpdate.setOriginLng(request.getOriginLng());
        flightToUpdate.setDestinationLat(request.getDestinationLat());
        flightToUpdate.setDestinationLng(request.getDestinationLng());
        flightToUpdate.setOriginName(request.getOriginName());
        flightToUpdate.setDestinationName(request.getDestinationName());
        flightToUpdate.setPrice(request.getPrice());
        flightToUpdate.setAeroLine(request.getAeroLine());

        var flightUpdated = flightRepository.save(flightToUpdate);
        return this.entityToResponse(flightUpdated);
    }

    private FlightResponse entityToResponse(FlightEntity entity) {
        FlightResponse response = new FlightResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}