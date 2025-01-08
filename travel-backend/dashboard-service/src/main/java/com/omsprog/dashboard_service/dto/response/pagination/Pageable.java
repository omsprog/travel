package com.omsprog.dashboard_service.dto.response.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pageable {
    private Sort sort;
    private int pageNumber;
    private int pageSize;
    private int offset;
    private boolean paged;
    private boolean unpaged;
}
