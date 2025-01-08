package com.omsprog.dashboard_service.service.concrete_service;

import com.omsprog.dashboard_service.dto.request.TicketRequest;
import com.omsprog.dashboard_service.dto.response.FlightResponse;
import com.omsprog.dashboard_service.dto.response.TicketResponse;
import com.omsprog.dashboard_service.entity.jpa.TicketEntity;
import com.omsprog.dashboard_service.exception.RecordNotFoundException;
import com.omsprog.dashboard_service.helper.CustomerHelper;
import com.omsprog.dashboard_service.repository.UserRepository;
import com.omsprog.dashboard_service.repository.FlightRepository;
import com.omsprog.dashboard_service.repository.TicketRepository;
import com.omsprog.dashboard_service.service.abstract_service.ITicketService;
import com.omsprog.dashboard_service.util.TravelUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor // Creates the constructor for the dependency injection
public class TicketService implements ITicketService {
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;

    public static final BigDecimal charge_price_percentage = BigDecimal.valueOf(0.25);

    @Override
    public Page<TicketResponse> readAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return this.ticketRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public TicketResponse create(TicketRequest request) {
        var flight = flightRepository.findById(request.getIdFlight()).orElseThrow(() -> new RecordNotFoundException("flight"));
        var customer = userRepository.findById(request.getIdClient()).orElseThrow(() -> new RecordNotFoundException("customer"));

        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .flight(flight)
                .customer(customer)
                .price(flight.getPrice().add(flight.getPrice().multiply(charge_price_percentage)))
                .purchaseDate(LocalDate.now())
                .departureDate(TravelUtil.getRandomSoon())
                .arrivalDate(TravelUtil.getRandomLatter())
                .build();

        var ticketPersisted = this.ticketRepository.save(ticketToPersist);

        log.info("Ticket saved with id: {}", ticketPersisted.getId());

        customerHelper.increase(customer.getDni(), TicketService.class);

        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticketFromDB = this.ticketRepository.findById(uuid).orElseThrow(() -> new RecordNotFoundException("ticket"));
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        var ticketToUpdate = ticketRepository.findById(uuid).orElseThrow(() -> new RecordNotFoundException("ticket"));
        var flight = flightRepository.findById(request.getIdFlight()).orElseThrow(() -> new RecordNotFoundException("flight"));

        ticketToUpdate.setFlight(flight);
        ticketToUpdate.setPrice(flight.getPrice().add(flight.getPrice().multiply(charge_price_percentage)));
        ticketToUpdate.setDepartureDate(TravelUtil.getRandomSoon());
        ticketToUpdate.setArrivalDate(TravelUtil.getRandomLatter());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);

        log.info("Ticket updated with id {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var ticketToDelete = ticketRepository.findById(uuid).orElseThrow(() -> new RecordNotFoundException("ticket"));
        this.ticketRepository.delete(ticketToDelete);
    }

    @Override
    public BigDecimal findPrice(Long idFlight) {
        var flight = this.flightRepository.findById(idFlight).orElseThrow(() -> new RecordNotFoundException("flight"));
        return flight.getPrice().add(flight.getPrice().multiply(charge_price_percentage));
    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        TicketResponse ticketResponse = new TicketResponse();
        BeanUtils.copyProperties(entity, ticketResponse);
        FlightResponse flightResponse = new FlightResponse();
        BeanUtils.copyProperties(entity.getFlight(), flightResponse);
        ticketResponse.setFlight(flightResponse);
        return ticketResponse;
    }
}
