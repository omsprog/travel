package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.dto.response.FlightResponse;
import com.omsprog.travel.entity.jpa.FlyEntity;
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

@Transactional(readOnly = true)
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

    private FlightResponse entityToResponse(FlyEntity entity) {
        FlightResponse response = new FlightResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}