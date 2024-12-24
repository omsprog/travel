package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.dto.request.CustomerRequest;
import com.omsprog.travel.dto.response.CustomerResponse;
import com.omsprog.travel.entity.jpa.CustomerEntity;
import com.omsprog.travel.exception.CustomValidationException;
import com.omsprog.travel.repository.CustomerRepository;
import com.omsprog.travel.service.abstract_service.ICustomerService;
import com.omsprog.travel.util.SortType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional()
@Service
@Slf4j
@AllArgsConstructor // Creates the constructor for the dependency injection
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder encoder;

    @Override
    public Page<CustomerResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;

        switch(sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        return this.customerRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public CustomerResponse create(CustomerRequest request) {
        if(customerRepository.findByDni(request.getDni()).isPresent()) {
            throw new CustomValidationException("Dni is already in use");
        }

        if(customerRepository.findByEmail(request.getEmail()).isPresent())
            throw new CustomValidationException("Email already exists");

        CustomerEntity entityToBePersisted = CustomerEntity.builder()
                .dni(request.getDni())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .totalFlights(0)
                .totalLodgings(0)
                .totalTours(0)
                .password(encoder.encode(request.getPassword()))
                .build();
        return this.entityToResponse(customerRepository.save(entityToBePersisted));
    }

    private CustomerResponse entityToResponse(CustomerEntity entity) {
        CustomerResponse response = new CustomerResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
