package com.omsprog.travel.service.abstract_service;

import com.omsprog.travel.dto.response.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse> {
    Set<FlyResponse> readByOriginDestination(String origin, String destination);
}
