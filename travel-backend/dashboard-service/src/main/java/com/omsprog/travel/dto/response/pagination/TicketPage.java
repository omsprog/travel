package com.omsprog.travel.dto.response.pagination;

import com.omsprog.travel.dto.response.TicketResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketPage extends CommonPage {
    private List<TicketResponse> content;
}
