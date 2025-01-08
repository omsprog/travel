package com.omsprog.travel.dto.response.pagination;

import com.omsprog.travel.dto.response.HotelResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HotelPage extends CommonPage {
    private List<HotelResponse> content;
}
