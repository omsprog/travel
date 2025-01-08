package com.omsprog.dashboard_service.dto.response.pagination;

import com.omsprog.dashboard_service.dto.response.HotelResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HotelPage extends CommonPage {
    private List<HotelResponse> content;
}
