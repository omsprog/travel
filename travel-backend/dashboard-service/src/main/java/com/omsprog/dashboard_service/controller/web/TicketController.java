package com.omsprog.dashboard_service.controller.web;

import com.omsprog.dashboard_service.dto.request.TicketRequest;
import com.omsprog.dashboard_service.dto.response.TicketResponse;
import com.omsprog.dashboard_service.error_handler.ErrorsResponse;
import com.omsprog.dashboard_service.service.abstract_service.ITicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path="tickets")
@AllArgsConstructor
@Tag(name = "ticket")
public class TicketController {
    private final ITicketService ticketService;

    @GetMapping
    public ResponseEntity<Page<TicketResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        var response = this.ticketService.readAll(page, size);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @ApiResponse(
            responseCode = "400",
            description = "When the request has an invalid field the response is handled by the ControllerAdvice",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
            }
    )
    @Operation(summary = "Saves a ticket")
    @PostMapping
    public ResponseEntity<TicketResponse> create(
            @RequestBody TicketRequest request
    ) {
        return ResponseEntity.ok(ticketService.create(request));
    }

    @GetMapping(path="{id}")
    public ResponseEntity<TicketResponse> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(ticketService.read(id));
    }

    @PutMapping(path="{id}")
    public ResponseEntity<TicketResponse> put(
            @PathVariable UUID id,
            @RequestBody TicketRequest request
    ) {
        return ResponseEntity.ok(this.ticketService.update(request, id));
    }

    @DeleteMapping(path="{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/price")
    public ResponseEntity<Map<String, BigDecimal>> getFlightPrice(
            @RequestParam Long flightId
    ) {
        return ResponseEntity.ok(Collections.singletonMap("flightPrice", this.ticketService.findPrice(flightId)));
    }
}
