package com.omsprog.dashboard_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationRequest {
    @Size(min = 10, max = 20, message = "The size has to be between 18 an 20 characters")
    @NotBlank(message = "Id client is mandatory")
    private String idClient;
    @Positive
    @NotNull(message = "idHotel is mandatory")
    private Long idHotel;
    @Min(value = 1, message = "Minimum 1 day to make a reservation")
    @Max(value = 30, message = "Maximum 30 days to make a reservation")
    @NotNull(message = "totalDays is mandatory")
    private Integer totalDays;
    //@Pattern(regexp = "^(.+)@(.+)$")
    @Email(message = "Invalid email")
    private String email;
}