package com.omsprog.dashboard_service.controller.web;

import com.omsprog.dashboard_service.dto.request.ReservationRequest;
import com.omsprog.dashboard_service.dto.response.ReservationResponse;
import com.omsprog.dashboard_service.error_handler.ErrorsResponse;
import com.omsprog.dashboard_service.service.abstract_service.IReservationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path="reservations")
@AllArgsConstructor
@Tag(name = "reservation")
public class ReservationController {

    private final IReservationService reservationService;

    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        var response = this.reservationService.readAll(page, size);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path="{id}")
    public ResponseEntity<ReservationResponse> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(reservationService.read(id));
    }

    @ApiResponse(
            responseCode = "400",
            description = "When the request has an invalid field the response is handled by the ControllerAdvice",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
            }
    )
    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody ReservationRequest request
    ) {
        return ResponseEntity.ok(reservationService.create(request));
    }

    @PutMapping(path="{id}")
    public ResponseEntity<ReservationResponse> put(
            @PathVariable UUID id,
            @Valid @RequestBody ReservationRequest request
    ) {
        return ResponseEntity.ok(this.reservationService.update(request, id));
    }

    @DeleteMapping(path="{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        this.reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/price")
    public ResponseEntity<Map<String, BigDecimal>> getHotelPrice(
            @RequestParam Long hotelId
    ) {
        return ResponseEntity.ok(Collections.singletonMap("hotelPrice", this.reservationService.findPrice(hotelId)));
    }
}
