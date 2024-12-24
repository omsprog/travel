package com.omsprog.travel.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerRequest {
    @NotNull(message = "DNI is mandatory")
    @Size(min = 18, max = 18, message = "DNI should be 18 characters long")
    private String dni;
    @NotNull(message = "Full Name is mandatory")
    @Size(min = 4, max = 20)
    private String fullName;
    @NotNull(message = "Phone number is mandatory")
    @Size(min = 10, max = 10, message = "Phone number should be 10 character long")
    private String phoneNumber;
    @NotNull(message = "Email is mandatory")
    @Email(message = "Not a valid email")
    private String email;
}