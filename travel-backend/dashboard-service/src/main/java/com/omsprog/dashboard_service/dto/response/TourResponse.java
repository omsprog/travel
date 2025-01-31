package com.omsprog.dashboard_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourResponse {
    public Long id;
    public String name;
    public UserResponse customer;
    public Set<UUID> tickets;
    private Set<UUID> reservations;
}