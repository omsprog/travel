package com.omsprog.dashboard_service.service.abstract_service;

import com.omsprog.dashboard_service.dto.request.UserRequest;
import com.omsprog.dashboard_service.dto.request.LoginRequest;
import com.omsprog.dashboard_service.dto.request.LoginResponse;
import com.omsprog.dashboard_service.dto.response.UserResponse;
import com.omsprog.dashboard_service.util.SortType;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IUserService {
    Page<UserResponse> readAll(Integer page, Integer size, SortType sortType);
    UserResponse create(UserRequest request);
    LoginResponse signIn(LoginRequest loginRequest);
    UserResponse getProfile(String email);
    String uploadProfilePicture(String email, MultipartFile file) throws IOException;
    Resource getProfilePicture(String email) throws MalformedURLException;
    String FIELD_BY_SORT = "fullName";
}