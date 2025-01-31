package com.omsprog.dashboard_service.helper;

import com.omsprog.dashboard_service.entity.jpa.*;
import com.omsprog.dashboard_service.repository.ReservationRepository;
import com.omsprog.dashboard_service.repository.TicketRepository;
import com.omsprog.dashboard_service.service.concrete_service.ReservationService;
import com.omsprog.dashboard_service.service.concrete_service.TicketService;
import com.omsprog.dashboard_service.util.TravelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    public Set<TicketEntity> createTickets(Set<FlightEntity> flights, AppUserEntity customer) {
        var response = new HashSet<TicketEntity>(flights.size());
        flights.forEach(flight -> {
            var ticketToPersist = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .flight(flight)
                    .customer(customer)
                    .price(flight.getPrice().add(flight.getPrice().multiply(TicketService.charge_price_percentage)))
                    .purchaseDate(LocalDate.now())
                    .departureDate(TravelUtil.getRandomSoon())
                    .arrivalDate(TravelUtil.getRandomLatter())
                    .arrivalDate(TravelUtil.getRandomLatter())
                    .build();
            response.add(this.ticketRepository.save(ticketToPersist));
        });
        return response;
    }

    public Set<ReservationEntity> createReservations(HashMap<HotelEntity, Integer> hotels, AppUserEntity customer) {
        var response = new HashSet<ReservationEntity>(hotels.size());
        hotels.forEach((hotel, totalDays) -> {
            var reservationToPersist = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .hotel(hotel)
                    .customer(customer)
                    .totalDays(totalDays)
                    .dateTimeReservation(LocalDateTime.now())
                    .dateStart(LocalDate.now())
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.charge_price_percentage)))
                    .build();
            response.add(this.reservationRepository.save(reservationToPersist));
        });
        return response;
    }

    public TicketEntity createTicket(FlightEntity flight, AppUserEntity customer) {
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .flight(flight)
                .customer(customer)
                .price(flight.getPrice().add(flight.getPrice().multiply(TicketService.charge_price_percentage)))
                .purchaseDate(LocalDate.now())
                .departureDate(TravelUtil.getRandomSoon())
                .arrivalDate(TravelUtil.getRandomLatter())
                .arrivalDate(TravelUtil.getRandomLatter())
                .build();
        return  this.ticketRepository.save(ticketToPersist);
    }

    public ReservationEntity createReservation(HotelEntity hotel, AppUserEntity customer, Integer totalDays) {
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(totalDays)
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.charge_price_percentage)))
                .build();

        return this.reservationRepository.save(reservationToPersist);
    }
}