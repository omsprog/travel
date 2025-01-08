package com.omsprog.dashboard_service.helper;

import com.omsprog.dashboard_service.exception.RecordNotFoundException;
import com.omsprog.dashboard_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@AllArgsConstructor
public class CustomerHelper {

    private final UserRepository userRepository;

    public void increase(String customerId, Class<?> type) {
        var customerToUpdate = this.userRepository.findById(customerId).orElseThrow(() -> new RecordNotFoundException("user"));
        switch (type.getSimpleName()) {
            case "TourService" -> customerToUpdate.setTotalTours(customerToUpdate.getTotalTours() + 1);
            case "TicketService" -> customerToUpdate.setTotalFlights(customerToUpdate.getTotalFlights() + 1);
            case "ReservationService" -> customerToUpdate.setTotalLodgings(customerToUpdate.getTotalLodgings() + 1);
        }
        this.userRepository.save(customerToUpdate);
    }
}