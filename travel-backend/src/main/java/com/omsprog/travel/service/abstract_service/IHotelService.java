package com.omsprog.travel.service.abstract_service;

import com.omsprog.travel.dto.request.HotelRequest;
import com.omsprog.travel.dto.response.HotelResponse;
import com.omsprog.travel.util.SortType;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse> {
    Set<HotelResponse> readGreaterThan(Integer rating);
    HotelResponse create(HotelRequest request);
    HotelResponse read(Long id);
    HotelResponse update(HotelRequest request, Long id);
    Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType);
}