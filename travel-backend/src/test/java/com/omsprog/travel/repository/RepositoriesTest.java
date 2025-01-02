package com.omsprog.travel.repository;

import com.omsprog.travel.entity.jpa.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class RepositoriesTest {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TourRepository tourRepository;

    @Test
    public void jpqlMethods() {
        var flightsLessThan = this.flightRepository.selectLessPrice(BigDecimal.valueOf(15.00));
        flightsLessThan.stream().forEach(ele -> assertTrue(ele.getPrice().intValue() < 15));

        var flightsBetween15And20 = this.flightRepository.selectBetweenPrice(BigDecimal.valueOf(15.00), BigDecimal.valueOf(20.00));
        flightsBetween15And20.stream().forEach(ele -> assertTrue(ele.getPrice().intValue() >= 15 && ele.getPrice().intValue() <= 20));
    }

    @Test
    public void jpaQueryMethods() {
        var hotelLessThan50 = this.hotelRepository.findByPriceLessThan(BigDecimal.valueOf(50.00));
        assertEquals(7, hotelLessThan50.size());
    }

    @Test
    public void createTour() {
        AppUserEntity customer = this.userRepository.findById("KEMI771012EUMRG004").get();

        TourEntity tourToBeCreated = TourEntity
                .builder()
                .customer(customer)
                .name("Unforgettable Tour")
                .build();

        // 1 ADD RESERVATION TO A TOUR

        HotelEntity hotel = this.hotelRepository.findById(3L).get();

        ReservationEntity reservation = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now().plusDays(2))
                .hotel(hotel)
                .customer(customer)
                .tour(tourToBeCreated)
                .totalDays(1)
                .price(hotel.getPrice().multiply(BigDecimal.TEN))
                .build();

        tourToBeCreated.addReservation(reservation);

        // 2 ADD TICKET TO A TOUR

        FlightEntity flight = this.flightRepository.findById(11L).get();

        TicketEntity ticketToBeCreated = TicketEntity
                .builder()
                .id(UUID.randomUUID())
                .price(flight.getPrice().multiply(BigDecimal.TEN))
                .arrivalDate(LocalDateTime.now())
                .departureDate(LocalDateTime.now())
                .purchaseDate(LocalDate.now())
                .customer(customer)
                .tour(tourToBeCreated)
                .flight(flight)
                .build();

        tourToBeCreated.addTicket(ticketToBeCreated);

        // 3 SAVES THE RESERVATION, THE TICKET AND THE TOUR INTO THE DATABASE
        // AND ITS CORRESPONDING RELATIONSHIPS (FKS)
        this.tourRepository.save(tourToBeCreated);
    }
}