package com.omsprog.dashboard_service.dto.request;

import com.omsprog.dashboard_service.util.AeroLine;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlightRequest {
    @NotNull(message = "Origin latitude is mandatory")
    @Min(value = -90, message = "Origin latitude must be between -90 and 90")
    @Max(value = 90, message = "Origin latitude must be between -90 and 90")
    private Double originLat;
    @NotNull(message = "Origin longitude is mandatory")
    @Min(value = -180, message = "Origin longitude must be between -180 and 180")
    @Max(value = 180, message = "Origin longitude must be between -180 and 180")
    private Double originLng;
    @NotNull(message = "Destination latitude is mandatory")
    @Min(value = -90, message = "Destination latitude must be between -90 and 90")
    @Max(value = 90, message = "Destination latitude must be between -90 and 90")
    private Double destinationLat;
    @NotNull(message = "Destination longitude is mandatory")
    @Min(value = -180, message = "Destination longitude must be between -180 and 180")
    @Max(value = 180, message = "Destination longitude must be between -180 and 180")
    private Double destinationLng;
    @NotNull(message = "Origin Name is mandatory")
    @Size(min = 4, max = 30, message = "Origin Name should be between 4 an 30 characters")
    private String originName;
    @NotNull(message = "Destination Name is mandatory")
    @Size(min = 4, max = 30, message = "Destination Name should be between 4 an 30 characters")
    private String destinationName;
    @NotNull(message = "price is mandatory")
    @Min(value=40, message = "price should be bigger than 40")
    private BigDecimal price;
    @NotNull(message = "Aeroline is mandatory")
    private AeroLine aeroLine;
}
