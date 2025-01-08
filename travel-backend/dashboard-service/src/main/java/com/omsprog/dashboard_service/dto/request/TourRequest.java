package com.omsprog.dashboard_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourRequest {
    public String customerId;
    public String name;
    public Set<TourFlightRequest> flights;
    public Set<TourHotelRequest> hotels;
}