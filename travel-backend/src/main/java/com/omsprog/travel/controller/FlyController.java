package com.omsprog.travel.controller;

import com.omsprog.travel.dto.external_models.DogFactDto;
import com.omsprog.travel.dto.response.FlyResponse;
import com.omsprog.travel.helper.DogFactsHelper;
import com.omsprog.travel.service.abstract_service.IFlyService;
import com.omsprog.travel.util.SortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path="fly")
@AllArgsConstructor
@Tag(name = "fly")
public class FlyController {
    private final IFlyService flyService;
    private DogFactsHelper dogFactsHelper;

    @Operation(summary = "returns facts about dogs")
    @GetMapping(path = "random_dog_service")
    public DogFactDto randomDogService() {
        return this.dogFactsHelper.getDogFacts();
    }

    @GetMapping
    public ResponseEntity<Page<FlyResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType
            ) {
        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.flyService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "less_price")
    public ResponseEntity<Set<FlyResponse>> getLessPrice(
            @RequestParam BigDecimal price
    ) {
        var response = this.flyService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "between_price")
    public ResponseEntity<Set<FlyResponse>> getBetweenPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max
    ) {
        var response = this.flyService.readBetweenPrice(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "origin_destination")
    public ResponseEntity<Set<FlyResponse>> getOriginDesination(
            @RequestParam String origin,
            @RequestParam String destination
    ) {
        var response = this.flyService.readByOriginDestination(origin, destination);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}