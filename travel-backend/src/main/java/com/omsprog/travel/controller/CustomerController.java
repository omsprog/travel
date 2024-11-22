package com.omsprog.travel.controller;

import com.omsprog.travel.dto.response.CustomerResponse;
import com.omsprog.travel.service.abstract_service.ICustomerService;
import com.omsprog.travel.util.SortType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path="customers")
@AllArgsConstructor
@Tag(name = "customer")
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType
    ) {
        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.customerService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

}