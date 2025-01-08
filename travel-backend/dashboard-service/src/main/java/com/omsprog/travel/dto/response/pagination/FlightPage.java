package com.omsprog.travel.dto.response.pagination;

import com.omsprog.travel.dto.response.FlightResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlightPage extends CommonPage {
    private List<FlightResponse> content;
}
