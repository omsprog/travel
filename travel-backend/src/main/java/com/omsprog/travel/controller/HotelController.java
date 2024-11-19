package com.omsprog.travel.controller;

import com.omsprog.travel.dto.request.HotelRequest;
import com.omsprog.travel.dto.response.HotelResponse;
import com.omsprog.travel.service.abstract_service.IHotelService;
import com.omsprog.travel.util.SortType;
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
@RequestMapping(path="hotel")
@AllArgsConstructor
@Tag(name = "hotel")
public class HotelController {
    private final IHotelService hotelService;

    @GetMapping
    public ResponseEntity<Page<HotelResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType
    ) {
        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.hotelService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path="{id}")
    public ResponseEntity<HotelResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.read(id));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> create(@Valid @RequestBody HotelRequest hotelRequest) {
        return ResponseEntity.ok(hotelService.create(hotelRequest));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<HotelResponse> put(
            @PathVariable Long id,
            @Valid @RequestBody HotelRequest hotelRequest
    ) {
        return ResponseEntity.ok(hotelService.update(hotelRequest, id));
    }

    @GetMapping(path = "less_price")
    public ResponseEntity<Set<HotelResponse>> getLessPrice(
            @RequestParam BigDecimal price
    ) {
        var response = this.hotelService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "between_price")
    public ResponseEntity<Set<HotelResponse>> getBetweenPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max
    ) {
        var response = this.hotelService.readBetweenPrice(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "rating_greater_than")
    public ResponseEntity<Set<HotelResponse>> getByRatingGreaterThan(
            @RequestParam Integer rating
    ) {
        var response = this.hotelService.readGreaterThan(rating);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}