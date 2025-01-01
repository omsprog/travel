package com.omsprog.travel.controller;

import com.omsprog.travel.dto.request.UserRequest;
import com.omsprog.travel.dto.request.LoginRequest;
import com.omsprog.travel.dto.request.LoginResponse;
import com.omsprog.travel.dto.response.UserResponse;
import com.omsprog.travel.service.abstract_service.IUserService;
import com.omsprog.travel.util.SortType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping(path="users")
@AllArgsConstructor
@Tag(name = "users")
public class UserController {
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader(required = false) SortType sortType
    ) {
        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.userService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.create(userRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> singIn(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.signIn(loginRequest));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(this.userService.getProfile(userDetails.getUsername()));
    }

    @PostMapping("/upload-picture")
    public ResponseEntity<String> uploadPicture(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(this.userService.uploadProfilePicture(userDetails.getUsername(), file));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/profile-picture")
    public ResponseEntity<Resource> getProfilePicture(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                    .body(this.userService.getProfilePicture(userDetails.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}