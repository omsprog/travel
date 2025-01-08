package com.omsprog.travel.entity.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketEntity {

    @Id
    private UUID id;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private LocalDate purchaseDate;
    private BigDecimal price;

    // RELATIONSHIPS
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = true)
    private TourEntity tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private AppUserEntity customer;
}