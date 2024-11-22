package com.omsprog.travel.helper;

import com.omsprog.travel.entity.jpa.*;
import com.omsprog.travel.repository.ReservationRepository;
import com.omsprog.travel.repository.TicketRepository;
import com.omsprog.travel.service.concrete_service.ReservationService;
import com.omsprog.travel.service.concrete_service.TicketService;
import com.omsprog.travel.util.TravelUtil;
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

    public Set<TicketEntity> createTickets(Set<FlightEntity> flights, CustomerEntity customer) {
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

    public Set<ReservationEntity> createReservations(HashMap<HotelEntity, Integer> hotels, CustomerEntity customer) {
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

    public TicketEntity createTicket(FlightEntity flight, CustomerEntity customer) {
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

    public ReservationEntity createReservation(HotelEntity hotel, CustomerEntity customer, Integer totalDays) {
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