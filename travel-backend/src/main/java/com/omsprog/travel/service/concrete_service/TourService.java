package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.dto.request.TourRequest;
import com.omsprog.travel.dto.response.CustomerResponse;
import com.omsprog.travel.dto.response.TourResponse;
import com.omsprog.travel.entity.jpa.*;
import com.omsprog.travel.helper.CustomerHelper;
import com.omsprog.travel.helper.TourHelper;
import com.omsprog.travel.repository.CustomerRepository;
import com.omsprog.travel.repository.FlightRepository;
import com.omsprog.travel.repository.HotelRepository;
import com.omsprog.travel.repository.TourRepository;
import com.omsprog.travel.service.abstract_service.ITourService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@AllArgsConstructor // Creates the constructor for the dependency injection
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final FlightRepository flightRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;
    private final CustomerHelper customerHelper;

    @Override
    public TourResponse create(TourRequest request) {
        var customer = customerRepository.findById(request.getCustomerId()).orElseThrow();
        var flights = new HashSet<FlightEntity>();
        request.getFlights().forEach(flight ->
                flights.add(this.flightRepository.findById(flight.getId()).orElseThrow())
        );
        var hotels = new HashMap<HotelEntity, Integer>();
        request.getHotels().forEach(hotel -> hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(), hotel.getTotalDays()));

        var tourToSave = TourEntity.builder()
                .name(request.getName())
                .tickets(this.tourHelper.createTickets(flights, customer))
                .reservations(this.tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();

        var tourSaved = this.tourRepository.save(tourToSave);

        customerHelper.increase(customer.getDni(), TourService.class);

        return this.entityToResponse(tourSaved);
    }

    @Override
    public TourResponse read(Long id) {
        var tourFromDb = this.tourRepository.findById(id).orElseThrow();
        return this.entityToResponse(tourFromDb);
    }

    @Override
    public void delete(Long id) {
        var tourToDelete = this.tourRepository.findById(id).orElseThrow();
        this.tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(Long tourId, UUID ticketId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        tourUpdate.removeTicket(ticketId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long flightId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        var flight = this.flightRepository.findById(flightId).orElseThrow();
        var ticket = this.tourHelper.createTicket(flight, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);

        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        tourUpdate.removeReservation(reservationId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addReservation(Long tourId, Long hotelId, Integer totalDays) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow();
        var reservation = this.tourHelper.createReservation(hotel, tourUpdate.getCustomer(), totalDays);
        tourUpdate.addReservation(reservation);
        this.tourRepository.save(tourUpdate);
        return reservation.getId();
    }

    private TourResponse entityToResponse(TourEntity entity) {
        TourResponse tourResponse = new TourResponse();
        BeanUtils.copyProperties(entity, tourResponse);
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(entity.getCustomer(), customerResponse);
        tourResponse.setCustomer(customerResponse);
        tourResponse.setReservations(entity.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()));
        tourResponse.setTickets(entity.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()));
        return tourResponse;
    }
}