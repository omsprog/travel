package com.omsprog.dashboard_service.service.concrete_service;

import com.omsprog.dashboard_service.dto.request.TourRequest;
import com.omsprog.dashboard_service.dto.response.UserResponse;
import com.omsprog.dashboard_service.dto.response.TourResponse;
import com.omsprog.dashboard_service.dto.response.TourSummaryResponse;
import com.omsprog.dashboard_service.entity.jpa.*;
import com.omsprog.dashboard_service.exception.RecordNotFoundException;
import com.omsprog.dashboard_service.helper.CustomerHelper;
import com.omsprog.dashboard_service.helper.TourHelper;
import com.omsprog.dashboard_service.repository.UserRepository;
import com.omsprog.dashboard_service.repository.FlightRepository;
import com.omsprog.dashboard_service.repository.HotelRepository;
import com.omsprog.dashboard_service.repository.TourRepository;
import com.omsprog.dashboard_service.service.abstract_service.ITourService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final UserRepository userRepository;
    private final TourHelper tourHelper;
    private final CustomerHelper customerHelper;

    @Override
    public Page<TourSummaryResponse> readAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return this.tourRepository.findTourSummary(pageRequest);
    }

    @Override
    public TourResponse create(TourRequest request) {
        var customer = userRepository.findById(request.getCustomerId()).orElseThrow(() -> new RecordNotFoundException("customer"));
        var flights = new HashSet<FlightEntity>();
        request.getFlights().forEach(flight ->
                flights.add(this.flightRepository.findById(flight.getId()).orElseThrow(() -> new RecordNotFoundException("flight")))
        );
        var hotels = new HashMap<HotelEntity, Integer>();
        request.getHotels().forEach(hotel ->
                hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(() -> new RecordNotFoundException("hotel")), hotel.getTotalDays())
        );

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
        var tourFromDb = this.tourRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("tour"));
        return this.entityToResponse(tourFromDb);
    }

    @Override
    public void delete(Long id) {
        var tourToDelete = this.tourRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("tour"));
        this.tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(Long tourId, UUID ticketId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(() -> new RecordNotFoundException("tour"));
        tourUpdate.removeTicket(ticketId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long flightId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(() -> new RecordNotFoundException("tour"));
        var flight = this.flightRepository.findById(flightId).orElseThrow(() -> new RecordNotFoundException("flight"));
        var ticket = this.tourHelper.createTicket(flight, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);

        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(() -> new RecordNotFoundException("tour"));
        tourUpdate.removeReservation(reservationId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addReservation(Long tourId, Long hotelId, Integer totalDays) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(() -> new RecordNotFoundException("tour"));
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new RecordNotFoundException("hotel"));
        var reservation = this.tourHelper.createReservation(hotel, tourUpdate.getCustomer(), totalDays);
        tourUpdate.addReservation(reservation);
        this.tourRepository.save(tourUpdate);
        return reservation.getId();
    }

    private TourResponse entityToResponse(TourEntity entity) {
        TourResponse tourResponse = new TourResponse();
        BeanUtils.copyProperties(entity, tourResponse);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(entity.getCustomer(), userResponse);
        tourResponse.setCustomer(userResponse);
        tourResponse.setReservations(entity.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()));
        tourResponse.setTickets(entity.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()));
        return tourResponse;
    }
}