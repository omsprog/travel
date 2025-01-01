package com.omsprog.travel.controller.weblayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omsprog.travel.controller.UserController;
import com.omsprog.travel.dto.request.UserRequest;
import com.omsprog.travel.dto.response.UserResponse;
import com.omsprog.travel.error_handler.ErrorsResponse;
import com.omsprog.travel.security.UserDetailsServiceImpl;
import com.omsprog.travel.security.jwt.JwtUtils;
import com.omsprog.travel.service.concrete_service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService customerService;
    @MockBean
    JwtUtils jwtUtils;
    @MockBean
    UserDetailsServiceImpl userDetailsService;

    private final static String signUpUrl = "/users/signup";

    static UserRequest getValidUserRequest() {
        return UserRequest.builder()
                .dni("TSAU967823OYONE740")
                .fullName("Test Automation")
                .phoneNumber("5567390326")
                .email("testautomation@gmail.com")
                .password("automationPass")
                .build();
    }

    @Test
    @DisplayName("User can be created")
    @Order(1)
    void validUser_whenCreateUser_returnsCreatedUser() throws Exception {
        // Arrange
        UserRequest userRequest = getValidUserRequest();

        UserResponse userResponse = UserResponse.builder()
                .dni("TSAU967823OYONE740")
                .fullName("Test Automation")
                .phoneNumber("5567390326")
                .totalFlights(0)
                .totalLodgings(0)
                .totalTours(0)
                .email("testautomation@gmail.com")
                .build();

        when(customerService.create(any(UserRequest.class)))
            .thenReturn(userResponse);
        when(jwtUtils.validateJwtToken(any()))
            .thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(any()))
            .thenReturn("testautomation@gmail.com");
        when(userDetailsService.loadUserByUsername(any()))
            .thenReturn(
                    new User(
                "testautomation@gmail.com",
                "automationPass",
                true,
                true,
                true,
                true,
                Collections.emptyList())
            );

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(signUpUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequest));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserResponse createdCustomer = new ObjectMapper()
                .readValue(responseBodyAsString, UserResponse.class);

        // Assert
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Controller returns 200 Status Code");
        assertEquals(userRequest.getDni(), createdCustomer.getDni());
        assertEquals(userRequest.getFullName(), createdCustomer.getFullName());
        assertEquals(userRequest.getPhoneNumber(), createdCustomer.getPhoneNumber());
        assertEquals(userRequest.getEmail(), createdCustomer.getEmail());
    }

    @Test
    @DisplayName("Customer Controller validation requests")
    @Order(2)
    void invalidCustomerRequests_whenCreateCustomer_returns400AndValidationMessages() throws Exception {
        // Arrange
        UserRequest userRequestDniRequiredValidation = getValidUserRequest();
        userRequestDniRequiredValidation.setDni(null);
        UserRequest userRequestDniNameLengthValidation = getValidUserRequest();
        userRequestDniNameLengthValidation.setDni("WICR9");

        UserRequest userRequestFullNameRequiredValidation = getValidUserRequest();
        userRequestFullNameRequiredValidation.setFullName(null);
        UserRequest userRequestFullNameLengthValidation = getValidUserRequest();
        userRequestFullNameLengthValidation.setFullName("Wil");

        UserRequest userRequestEmailRequiredValidation = getValidUserRequest();
        userRequestEmailRequiredValidation.setEmail(null);
        UserRequest userRequestInvalidEmailValidation = getValidUserRequest();
        userRequestInvalidEmailValidation.setEmail("yusyu7");

        // Act & Assert
        validateBadRequest(userRequestDniRequiredValidation, "DNI is mandatory");
        validateBadRequest(userRequestDniNameLengthValidation, "DNI should be 18 characters long");
        validateBadRequest(userRequestFullNameRequiredValidation, "Full Name is mandatory");
        validateBadRequest(userRequestFullNameLengthValidation, "Full Name should be between 4 an 30 characters");
        validateBadRequest(userRequestEmailRequiredValidation, "Email is mandatory");
        validateBadRequest(userRequestInvalidEmailValidation, "Not a valid email");
    }

    private void validateBadRequest(UserRequest userRequest, String expectedErrorMessage) throws Exception {
        // Prepare request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(signUpUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequest));

        // Execute request
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        ErrorsResponse errorsResponse = new ObjectMapper().readValue(responseBody, ErrorsResponse.class);

        // Validate response
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals(1, errorsResponse.getErrors().size());
        assertEquals(expectedErrorMessage, errorsResponse.getErrors().get(0));
    }
}