package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.dto.request.FlightRequest;
import com.omsprog.travel.dto.response.FlightResponse;
import com.omsprog.travel.entity.jpa.FlyEntity;
import com.omsprog.travel.exception.IdNotFoundException;
import com.omsprog.travel.repository.FlightRepository;
import com.omsprog.travel.service.abstract_service.IFlightService;
import com.omsprog.travel.util.CacheConstants;
import com.omsprog.travel.util.SortType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
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
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        return this.flightRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
//    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    public Set<FlightResponse> readLessPrice(BigDecimal price) {
        return this.flightRepository.selectLessPrice(price)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
//    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    public Set<FlightResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return this.flightRepository.selectBetweenPrice(min, max)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
//    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    public Set<FlightResponse> readByOriginDestination(String origin, String destination) {
        return this.flightRepository.selectOriginDestiny(origin, destination)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public FlightResponse create(FlightRequest request) {
        FlyEntity entityToBePersisted = FlyEntity.builder()
                .originLat(request.getOriginLat())
                .originLng(request.getOriginLng())
                .destinyLat(request.getDestinyLat())
                .destinyLng(request.getDestinyLng())
                .originName(request.getOriginName())
                .destinyName(request.getDestinyName())
                .price(request.getPrice())
                .aeroLine(request.getAeroLine())
                .build();
        return this.entityToResponse(this.flightRepository.save(entityToBePersisted));
    }

    @Override
    public FlightResponse read(Long id) {
        return this.entityToResponse(this.flightRepository.findById(id).orElse(null));
    }

    @Override
    public FlightResponse update(FlightRequest request, Long id) {
        var flightToUpdate = this.flightRepository.findById(id).orElseThrow(() -> new IdNotFoundException("flight"));

        flightToUpdate.setOriginLat(request.getOriginLat());
        flightToUpdate.setOriginLng(request.getOriginLng());
        flightToUpdate.setDestinyLat(request.getDestinyLat());
        flightToUpdate.setDestinyLng(request.getDestinyLng());
        flightToUpdate.setOriginName(request.getOriginName());
        flightToUpdate.setDestinyName(request.getDestinyName());
        flightToUpdate.setPrice(request.getPrice());
        flightToUpdate.setAeroLine(request.getAeroLine());

        var flightUpdated = flightRepository.save(flightToUpdate);
        return this.entityToResponse(flightUpdated);
    }

    private FlightResponse entityToResponse(FlyEntity entity) {
        FlightResponse response = new FlightResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}