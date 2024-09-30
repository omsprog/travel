package com.omsprog.travel.service.abstract_service;

import com.omsprog.travel.dto.response.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse> {
    Set<HotelResponse> readGreaterThan(Integer rating);
}