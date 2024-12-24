package com.omsprog.travel.dto.response.pagination;

import com.omsprog.travel.dto.response.CustomerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerPage extends CommonPage {
    private List<CustomerResponse> content;
}
