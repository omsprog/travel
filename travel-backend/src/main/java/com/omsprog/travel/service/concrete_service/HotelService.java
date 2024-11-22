package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.dto.request.HotelRequest;
import com.omsprog.travel.dto.response.HotelResponse;
import com.omsprog.travel.entity.jpa.HotelEntity;
import com.omsprog.travel.exception.IdNotFoundException;
import com.omsprog.travel.repository.HotelRepository;
import com.omsprog.travel.service.abstract_service.IHotelService;
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
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;

        switch(sortType) {
            case NONE, LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        return this.hotelRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
//    @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readGreaterThan(Integer rating) {
        return this.hotelRepository.findByRatingGreaterThan(rating)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public HotelResponse create(HotelRequest request) {
        var hotelToPersist = HotelEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .rating(request.getRating())
                .price(request.getPrice())
                .build();

        var hotelPersisted = this.hotelRepository.save(hotelToPersist);
        return this.entityToResponse(hotelPersisted);
    }

    @Override
    public HotelResponse read(Long id) {
        return this.entityToResponse(this.hotelRepository.findById(id).orElse(null));
    }

    @Override
    public HotelResponse update(HotelRequest request, Long id) {
        var hotelToUpdate = hotelRepository.findById(id).orElseThrow(() -> new IdNotFoundException("hotel"));

        hotelToUpdate.setName(request.getName());
        hotelToUpdate.setAddress(request.getAddress());
        hotelToUpdate.setRating(request.getRating());
        hotelToUpdate.setPrice(request.getPrice());

        var hotelUpdated = this.hotelRepository.save(hotelToUpdate);

        return this.entityToResponse(hotelUpdated);
    }

    @Override
//    @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return this.hotelRepository.findByPriceLessThan(price)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
//    @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
    public Set<HotelResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return this.hotelRepository.findByPriceIsBetween(min, max)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    private HotelResponse entityToResponse(HotelEntity entity) {
        HotelResponse response = new HotelResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}