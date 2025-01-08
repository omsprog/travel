package com.omsprog.dashboard_service.service.concrete_service;

import com.omsprog.dashboard_service.dto.request.ReservationRequest;
import com.omsprog.dashboard_service.dto.response.HotelResponse;
import com.omsprog.dashboard_service.dto.response.ReservationResponse;
import com.omsprog.dashboard_service.entity.jpa.ReservationEntity;
import com.omsprog.dashboard_service.exception.RecordNotFoundException;
import com.omsprog.dashboard_service.helper.CustomerHelper;
import com.omsprog.dashboard_service.repository.UserRepository;
import com.omsprog.dashboard_service.repository.HotelRepository;
import com.omsprog.dashboard_service.repository.ReservationRepository;
import com.omsprog.dashboard_service.service.abstract_service.IReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor // Creates the constructor for the dependency injection
public class ReservationService implements IReservationService {
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerHelper customerHelper;

    public static final BigDecimal charge_price_percentage = BigDecimal.valueOf(0.2);

    @Override
    public Page<ReservationResponse> readAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return this.reservationRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public ReservationResponse create(ReservationRequest request) {
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow(() -> new RecordNotFoundException("hotel"));
        var customer = this.userRepository.findById(request.getIdClient()).orElseThrow(() -> new RecordNotFoundException("customer"));

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charge_price_percentage)))
                .build();

        var reservationPersisted = this.reservationRepository.save(reservationToPersist);

        customerHelper.increase(customer.getDni(), ReservationService.class);

        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        var reservationFromDB = this.reservationRepository.findById(uuid).orElseThrow(() -> new RecordNotFoundException("reservation"));
        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid) {
        var reservationToUpdate = reservationRepository.findById(uuid).orElseThrow(() -> new RecordNotFoundException("reservation"));
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow(() -> new RecordNotFoundException("hotel"));

        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(charge_price_percentage)));

        var reservationUpdated = this.reservationRepository.save(reservationToUpdate);

        return this.entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var reservationToDelete = reservationRepository.findById(uuid).orElseThrow(() -> new RecordNotFoundException("reservation"));
        this.reservationRepository.delete(reservationToDelete);
    }

    @Override
    public BigDecimal findPrice(Long idFlight) {
        var hotel = this.hotelRepository.findById(idFlight).orElseThrow(() -> new RecordNotFoundException("hotel"));
        return hotel.getPrice().add(hotel.getPrice().multiply(charge_price_percentage));
    }

    private ReservationResponse entityToResponse(ReservationEntity entity) {
        ReservationResponse reservationResponse = new ReservationResponse();
        BeanUtils.copyProperties(entity, reservationResponse);
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);
        reservationResponse.setHotel(hotelResponse);
        return reservationResponse;
    }
}
