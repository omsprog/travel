package com.omsprog.travel.dto.response.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sort {
    private boolean sorted;
    private boolean empty;
    private boolean unsorted;
}
