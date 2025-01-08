package com.omsprog.travel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourSummaryResponse {
    private Long id;
    private String name;
    private String customerName;
    private Long ticketCount;
    private Long reservationCount;
}
