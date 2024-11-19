package com.omsprog.travel.dto.request;

import com.omsprog.travel.util.AeroLine;
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
    @Min(value = -180, message = "Origin latitude must be between -180 and 180")
    @Max(value = 180, message = "Origin latitude must be between -180 and 180")
    private Double originLng;
    @NotNull(message = "Destiny latitude is mandatory")
    @Min(value = -90, message = "Destiny latitude must be between -90 and 90")
    @Max(value = 90, message = "Destiny latitude must be between -90 and 90")
    private Double destinyLat;
    @NotNull(message = "Destiny longitude is mandatory")
    @Min(value = -180, message = "Destiny latitude must be between -180 and 180")
    @Max(value = 180, message = "Destiny latitude must be between -180 and 180")
    private Double destinyLng;
    @NotNull(message = "Origin Name is mandatory")
    @Size(min = 4, max = 30, message = "Origin Name should be between 4 an 30 characters")
    private String originName;
    @NotNull(message = "Destiny Name is mandatory")
    @Size(min = 4, max = 30, message = "Destiny Name should be between 4 an 30 characters")
    private String destinyName;
    @NotNull(message = "price is mandatory")
    @Positive(message = "price should be positive")
    private BigDecimal price;
    @NotNull(message = "Aeroline is mandatory")
    private AeroLine aeroLine;
}
