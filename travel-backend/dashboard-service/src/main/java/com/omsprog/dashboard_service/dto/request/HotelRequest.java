package com.omsprog.dashboard_service.dto.request;

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
    @NotNull(message = "Hotel name is mandatory")
    @Size(min = 4, max = 30, message = "Hotel name should be between 4 and 30 characters")
    private String name;
    @NotNull(message = "Hotel address is mandatory")
    @Size(min = 4, max = 30, message = "Hotel address should be between 4 and 30 characters")
    private String address;
    @NotNull(message = "rating is mandatory")
    @Positive(message = "rating should be positive")
    private Integer rating;
    @NotNull(message = "price is mandatory")
    @Positive(message = "price should be positive")
    private BigDecimal price;
}