package com.omsprog.dashboard_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private String address;
    private Integer rating;
    private BigDecimal price;
}