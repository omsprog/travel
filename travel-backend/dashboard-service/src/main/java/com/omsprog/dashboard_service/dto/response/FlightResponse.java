package com.omsprog.dashboard_service.dto.response;

import com.omsprog.dashboard_service.util.AeroLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlightResponse {
    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinationLat;
    private Double destinationLng;
    private String originName;
    private String destinationName;
    private BigDecimal price;
    private AeroLine aeroLine;
}