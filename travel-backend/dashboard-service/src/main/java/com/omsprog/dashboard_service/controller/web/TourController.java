package com.omsprog.dashboard_service.controller.web;

import com.omsprog.dashboard_service.dto.request.TourRequest;
import com.omsprog.dashboard_service.dto.response.TourResponse;
import com.omsprog.dashboard_service.dto.response.TourSummaryResponse;
import com.omsprog.dashboard_service.error_handler.ErrorsResponse;
import com.omsprog.dashboard_service.service.abstract_service.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "tours")
@AllArgsConstructor
@Tag(name = "tour")
public class TourController {

    private final ITourService tourService;

    @GetMapping
    public ResponseEntity<Page<TourSummaryResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        var response = this.tourService.readAll(page, size);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Saves a tour based on list of hotels and flights")
    @ApiResponse(
            responseCode = "400",
            description = "When the request has an invalid field the response is handled by the ControllerAdvice",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
            }
    )
    @PostMapping
    public ResponseEntity<TourResponse> post(@RequestBody TourRequest request) {
        return ResponseEntity.ok(this.tourService.create(request));
    }

    @Operation(summary = "Returns a Tour given the Id")
    @GetMapping(path = "{id}")
    public ResponseEntity<TourResponse> post(@PathVariable Long id) {
        return ResponseEntity.ok(this.tourService.read(id));
    }

    @Operation(summary = "Deletes a tour given the Id")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Removes a ticket from tour")
    @PatchMapping(path = "{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId, @PathVariable UUID ticketId) {
        this.tourService.removeTicket(tourId, ticketId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "{tourId}/add_ticket/{flightId}")
    public ResponseEntity<Map<String, UUID>> postTicket(@PathVariable Long tourId, @PathVariable Long flightId) {
        var response = Collections.singletonMap("ticketId", this.tourService.addTicket(tourId, flightId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long tourId, @PathVariable UUID reservationId) {
        this.tourService.removeReservation(tourId, reservationId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> postTicket(
            @PathVariable Long tourId,
            @PathVariable Long hotelId,
            @RequestParam Integer totalDays) {
        var response = Collections.singletonMap("ticketId", this.tourService.addReservation(tourId, hotelId, totalDays));
        return ResponseEntity.ok(response);
    }
}
