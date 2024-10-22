package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.dto.request.TicketRequest;
import com.omsprog.travel.dto.response.FlightResponse;
import com.omsprog.travel.dto.response.TicketResponse;
import com.omsprog.travel.entity.jpa.TicketEntity;
import com.omsprog.travel.helper.CustomerHelper;
import com.omsprog.travel.repository.CustomerRepository;
import com.omsprog.travel.repository.FlightRepository;
import com.omsprog.travel.repository.TicketRepository;
import com.omsprog.travel.service.abstract_service.ITicketService;
import com.omsprog.travel.util.TravelUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;

    public static final BigDecimal charge_price_percentage = BigDecimal.valueOf(0.25);

    @Override
    public TicketResponse create(TicketRequest request) {
        var fly = flightRepository.findById(request.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(charge_price_percentage)))
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
        var ticketFromDB = this.ticketRepository.findById(uuid).orElseThrow();
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        var ticketToUpdate = ticketRepository.findById(uuid).orElseThrow();
        var fly = flightRepository.findById(request.getIdFly()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(charge_price_percentage)));
        ticketToUpdate.setDepartureDate(TravelUtil.getRandomSoon());
        ticketToUpdate.setArrivalDate(TravelUtil.getRandomLatter());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);

        log.info("Ticket updated with id {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var ticketToDelete = ticketRepository.findById(uuid).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);
    }

    @Override
    public BigDecimal findPrice(Long idFly) {
        var fly = this.flightRepository.findById(idFly).orElseThrow();
        return fly.getPrice().add(fly.getPrice().multiply(charge_price_percentage));
    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        TicketResponse ticketResponse = new TicketResponse();
        BeanUtils.copyProperties(entity, ticketResponse);
        FlightResponse flightResponse = new FlightResponse();
        BeanUtils.copyProperties(entity.getFly(), flightResponse);
        ticketResponse.setFly(flightResponse);
        return ticketResponse;
    }
}
