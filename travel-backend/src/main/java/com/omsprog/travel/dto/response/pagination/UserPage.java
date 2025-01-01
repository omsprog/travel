package com.omsprog.travel.dto.response.pagination;

import com.omsprog.travel.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPage extends CommonPage {
    private List<UserResponse> content;
}