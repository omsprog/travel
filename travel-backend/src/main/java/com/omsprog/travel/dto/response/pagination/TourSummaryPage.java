package com.omsprog.travel.dto.response.pagination;

import com.omsprog.travel.dto.response.TourSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TourSummaryPage extends CommonPage {
    private List<TourSummaryResponse> content;
}
