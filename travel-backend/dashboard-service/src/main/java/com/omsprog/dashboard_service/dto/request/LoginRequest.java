package com.omsprog.dashboard_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginRequest {
    @NotNull(message = "Email is mandatory")
    @Email(message = "Not a valid email")
    @Size(max = 30, message = "Email should be less than 30 characters")
    private String email;
    @NotNull(message = "Password is mandatory")
    @Size(min = 6, max = 20, message = "Password should be between 6 and 20 characters")
    private String password;
}