package com.omsprog.travel.controller;

import com.omsprog.travel.dto.request.CustomerRequest;
import com.omsprog.travel.dto.request.LoginRequest;
import com.omsprog.travel.dto.request.LoginResponse;
import com.omsprog.travel.dto.response.CustomerResponse;
import com.omsprog.travel.repository.CustomerRepository;
import com.omsprog.travel.service.abstract_service.ICustomerService;
import com.omsprog.travel.util.SortType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path="users")
@AllArgsConstructor
@Tag(name = "users")
public class CustomerController {
    private final ICustomerService customerService;
    private final CustomerRepository customerRepository;

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

    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signUp(@Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.create(customerRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> singIn(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(customerService.signIn(loginRequest));
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(this.customerService.getProfile(userDetails.getUsername()));
    }
}