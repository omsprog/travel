package com.omsprog.travel.controller.weblayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omsprog.travel.controller.CustomerController;
import com.omsprog.travel.dto.request.CustomerRequest;
import com.omsprog.travel.dto.response.CustomerResponse;
import com.omsprog.travel.error_handler.ErrorsResponse;
import com.omsprog.travel.service.concrete_service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    private final static String signUpUrl = "/users/signup";

    static CustomerRequest getValidUserRequest() {
        return CustomerRequest.builder()
                .dni("WICR970328OPEBR891")
                .fullName("Craig Willer")
                .phoneNumber("5889816789")
                .email("craig@gmail.com")
                .password("userPass")
                .build();
    }

    @Test
    @DisplayName("User can be created")
    void validUser_whenCreateUser_returnsCreatedUser() throws Exception {
        // Arrange
        CustomerRequest customerRequest = getValidUserRequest();

        CustomerResponse customerResponse = CustomerResponse.builder()
                .dni("WICR970328OPEBR891")
                .fullName("Craig Willer")
                .phoneNumber("5889816789")
                .totalFlights(0)
                .totalLodgings(0)
                .totalTours(0)
                .email("craig@gmail.com")
                .build();

        when(customerService.create(any(CustomerRequest.class)))
                .thenReturn(customerResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(signUpUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customerRequest));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        CustomerResponse createdCustomer = new ObjectMapper()
                .readValue(responseBodyAsString, CustomerResponse.class);

        // Assert
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Controller returns 200 Status Code");
        assertEquals(customerRequest.getDni(), createdCustomer.getDni());
        assertEquals(customerRequest.getFullName(), createdCustomer.getFullName());
        assertEquals(customerRequest.getPhoneNumber(), createdCustomer.getPhoneNumber());
        assertEquals(customerRequest.getEmail(), createdCustomer.getEmail());
    }

    @Test
    @DisplayName("Customer Controller validation requests")
    void invalidCustomerRequests_whenCreateCustomer_returns400AndValidationMessages() throws Exception {
        // Arrange
        CustomerRequest customerRequestDniRequiredValidation = getValidUserRequest();
        customerRequestDniRequiredValidation.setDni(null);
        CustomerRequest customerRequestDniNameLengthValidation = getValidUserRequest();
        customerRequestDniNameLengthValidation.setDni("WICR9");

        CustomerRequest customerRequestFullNameRequiredValidation = getValidUserRequest();
        customerRequestFullNameRequiredValidation.setFullName(null);
        CustomerRequest customerRequestFullNameLengthValidation = getValidUserRequest();
        customerRequestFullNameLengthValidation.setFullName("Wil");

        CustomerRequest customerRequestEmailRequiredValidation = getValidUserRequest();
        customerRequestEmailRequiredValidation.setEmail(null);
        CustomerRequest customerRequestInvalidEmailValidation = getValidUserRequest();
        customerRequestInvalidEmailValidation.setEmail("yusyu7");

        // Act & Assert
        validateBadRequest(customerRequestDniRequiredValidation, "DNI is mandatory");
        validateBadRequest(customerRequestDniNameLengthValidation, "DNI should be 18 characters long");
        validateBadRequest(customerRequestFullNameRequiredValidation, "Full Name is mandatory");
        validateBadRequest(customerRequestFullNameLengthValidation, "Full Name should be between 4 an 30 characters");
        validateBadRequest(customerRequestEmailRequiredValidation, "Email is mandatory");
        validateBadRequest(customerRequestInvalidEmailValidation, "Not a valid email");
    }

    private void validateBadRequest(CustomerRequest customerRequest, String expectedErrorMessage) throws Exception {
        // Prepare request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(signUpUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customerRequest));

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