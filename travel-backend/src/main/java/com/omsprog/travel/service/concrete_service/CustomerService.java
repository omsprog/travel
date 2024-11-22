package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.dto.response.CustomerResponse;
import com.omsprog.travel.entity.jpa.CustomerEntity;
import com.omsprog.travel.repository.CustomerRepository;
import com.omsprog.travel.service.abstract_service.ICustomerService;
import com.omsprog.travel.util.SortType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional()
@Service
@Slf4j
@AllArgsConstructor // Creates the constructor for the dependency injection
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

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

    private CustomerResponse entityToResponse(CustomerEntity entity) {
        CustomerResponse response = new CustomerResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
