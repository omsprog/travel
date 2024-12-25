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
    @Size(min = 4, max = 20, message = "Full Name should be between 4 an 30 characters")
    private String fullName;
    @NotNull(message = "Phone number is mandatory")
    @Size(min = 10, max = 10, message = "Phone number should be 10 character long")
    private String phoneNumber;
    @NotNull(message = "Email is mandatory")
    @Email(message = "Not a valid email")
    @Size(max = 30, message = "Email should be less than 30 characters")
    private String email;
    @NotNull(message = "Password is mandatory")
    @Size(min = 6, max = 20, message = "Password should be between 6 and 20 characters")
    private String password;
}