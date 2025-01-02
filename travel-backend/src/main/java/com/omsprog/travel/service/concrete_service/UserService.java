package com.omsprog.travel.service.concrete_service;

import com.omsprog.travel.dto.request.UserRequest;
import com.omsprog.travel.dto.request.LoginRequest;
import com.omsprog.travel.dto.request.LoginResponse;
import com.omsprog.travel.dto.response.UserResponse;
import com.omsprog.travel.entity.jpa.AppUserEntity;
import com.omsprog.travel.exception.CustomValidationException;
import com.omsprog.travel.exception.RecordNotFoundException;
import com.omsprog.travel.repository.UserRepository;
import com.omsprog.travel.security.jwt.JwtUtils;
import com.omsprog.travel.service.abstract_service.IUserService;
import com.omsprog.travel.util.SortType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Transactional()
@Service
@Slf4j
@AllArgsConstructor // Creates the constructor for the dependency injection
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final Path uploadDir = Paths.get("profile-pictures");

    @Override
    public Page<UserResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;

        switch(sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        return this.userRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public UserResponse create(UserRequest request) {
        if(userRepository.findByDni(request.getDni()).isPresent()) {
            throw new CustomValidationException("Dni is already in use");
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent())
            throw new CustomValidationException("Email already exists");

        AppUserEntity entityToBePersisted = AppUserEntity.builder()
                .dni(request.getDni())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .totalFlights(0)
                .totalLodgings(0)
                .totalTours(0)
                .password(encoder.encode(request.getPassword()))
                .build();
        return this.entityToResponse(userRepository.save(entityToBePersisted));
    }

    @Override
    public LoginResponse signIn(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch(AuthenticationException e) {
            throw new CustomValidationException("Invalid email or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        return new LoginResponse(userDetails.getUsername(), jwtToken);
    }

    @Override
    public UserResponse getProfile(String email) {
        AppUserEntity customerProfileInfo = this.userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("user"));
        return this.entityToResponse(customerProfileInfo);
    }

    private void validateFile(MultipartFile file) {
        if(file.isEmpty()) {
            throw new CustomValidationException("File is empty");
        }

        if(!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new CustomValidationException("File is not an image");
        }

        if(file.getSize() > 5 * 1024 * 1024) {
            throw new CustomValidationException("File is too large. Exceed the 5MB limit");
        }
    }

    @Override
    public String uploadProfilePicture(String email, MultipartFile file) throws IOException {
        validateFile(file);

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path targetLocation = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        AppUserEntity appUserEntity = this.userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("user"));
        appUserEntity.setProfilePicturePath(targetLocation.toString());
        this.userRepository.save(appUserEntity);
        return fileName;
    }

    @Override
    public Resource getProfilePicture(String email) throws MalformedURLException {
        AppUserEntity customer = this.userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("user"));
        Path filePath = Paths.get(customer.getProfilePicturePath());
        return new UrlResource(filePath.toUri());
    }

    private UserResponse entityToResponse(AppUserEntity entity) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
