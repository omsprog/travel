package com.omsprog.dashboard_service.dto.response.pagination;

import com.omsprog.dashboard_service.dto.response.FlightResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlightPage extends CommonPage {
    private List<FlightResponse> content;
}
