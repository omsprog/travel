package com.omsprog.travel.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelRequest {
    @NotNull(message = "name is mandatory")
    @Size(min = 5, max = 30, message = "Name should be between 5 an 30 characters")
    private String name;
    @NotNull(message = "address is mandatory")
    @Size(min = 5, max = 30, message = "Address should be between 5 an 30 characters")
    private String address;
    @NotNull(message = "rating is mandatory")
    @Positive(message = "rating should be positive")
    private Integer rating;
    @NotNull(message = "price is mandatory")
    @Positive(message = "price should be positive")
    private BigDecimal price;
}