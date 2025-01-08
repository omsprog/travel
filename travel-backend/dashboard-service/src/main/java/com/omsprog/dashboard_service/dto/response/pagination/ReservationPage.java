package com.omsprog.dashboard_service.dto.response.pagination;

import com.omsprog.dashboard_service.dto.response.ReservationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationPage extends CommonPage {
    private List<ReservationResponse> content;
}
