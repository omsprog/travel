package com.omsprog.travel.service.abstract_service;

import com.omsprog.travel.dto.request.CustomerRequest;
import com.omsprog.travel.dto.response.CustomerResponse;
import com.omsprog.travel.util.SortType;
import org.springframework.data.domain.Page;

public interface ICustomerService {
    Page<CustomerResponse> readAll(Integer page, Integer size, SortType sortType);
    CustomerResponse create(CustomerRequest request);
    String FIELD_BY_SORT = "fullName";
}