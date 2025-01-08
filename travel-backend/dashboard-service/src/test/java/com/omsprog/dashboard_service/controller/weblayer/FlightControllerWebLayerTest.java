package com.omsprog.dashboard_service.controller.weblayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omsprog.dashboard_service.controller.web.FlightController;
import com.omsprog.dashboard_service.dto.request.FlightRequest;
import com.omsprog.dashboard_service.dto.response.FlightResponse;
import com.omsprog.dashboard_service.error_handler.ErrorsResponse;
import com.omsprog.dashboard_service.security.UserDetailsServiceImpl;
import com.omsprog.dashboard_service.security.jwt.JwtUtils;
import com.omsprog.dashboard_service.service.concrete_service.FlightService;
import com.omsprog.dashboard_service.util.AeroLine;
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

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FlightController.class)
@AutoConfigureMockMvc(addFilters = false)
class FlightControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FlightService flightService;
    @MockBean
    JwtUtils jwtUtils;
    @MockBean
    UserDetailsServiceImpl userDetailsService;

    private final static String flightsUrl = "/flights";

    static FlightRequest getValidFlightRequest() {
        return FlightRequest.builder()
                .originLat(80.9999)
                .originLng(88.8888)
                .destinationLat(22.2222)
                .destinationLng(11.1111)
                .originName("Mexico")
                .destinationName("Russia")
                .price(BigDecimal.valueOf(90))
                .aeroLine(AeroLine.aero_gold)
                .build();
    }

    @Test
    @DisplayName("Flight can be created")
    @Order(1)
    void validFlight_whenCreateFlight_returnsCreatedFlight() throws Exception {
        // Arrange
        FlightRequest flightRequest = getValidFlightRequest();

        FlightResponse flightResponse = FlightResponse.builder()
                .id(16L)
                .originLat(80.9999)
                .originLng(88.8888)
                .destinationLat(22.2222)
                .destinationLng(11.1111)
                .originName("Mexico")
                .destinationName("Russia")
                .price(BigDecimal.valueOf(90))
                .aeroLine(AeroLine.aero_gold)
                .build();

        when(flightService.create(any(FlightRequest.class)))
                .thenReturn(flightResponse);
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
                .post("/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(flightRequest));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        FlightResponse createdFlight = new ObjectMapper()
                .readValue(responseBodyAsString, FlightResponse.class);

        // Assert
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Controller returns 200 Status Code");
        assertEquals(flightRequest.getOriginLat(), createdFlight.getOriginLat());
        assertEquals(flightRequest.getOriginLng(), createdFlight.getOriginLng());
        assertEquals(flightRequest.getDestinationLat(), createdFlight.getDestinationLat());
        assertEquals(flightRequest.getDestinationLng(), createdFlight.getDestinationLng());
        assertEquals(flightRequest.getOriginName(), createdFlight.getOriginName());
        assertEquals(flightRequest.getDestinationName(), createdFlight.getDestinationName());
        assertEquals(flightRequest.getPrice(), createdFlight.getPrice());
        assertEquals(flightRequest.getAeroLine(), createdFlight.getAeroLine());
    }

    @Test
    @DisplayName("Flight Controller validation requests")
    @Order(2)
    void invalidFlightRequests_whenCreateFlight_returns400AndValidationMessages() throws Exception {
        // Arrange
        FlightRequest flightRequestOriginNameRequiredValidation = getValidFlightRequest();
        flightRequestOriginNameRequiredValidation.setOriginName(null);
        FlightRequest flightRequestOriginNameLengthValidation = getValidFlightRequest();
        flightRequestOriginNameLengthValidation.setOriginName("");

        FlightRequest flightRequestDestinationNameRequiredValidation = getValidFlightRequest();
        flightRequestDestinationNameRequiredValidation.setDestinationName(null);
        FlightRequest flightRequestDestinationNameLengthValidation = getValidFlightRequest();
        flightRequestDestinationNameLengthValidation.setDestinationName("Mia");

        // Act & Assert
        validateBadRequest(flightRequestOriginNameRequiredValidation, "Origin Name is mandatory");
        validateBadRequest(flightRequestOriginNameLengthValidation, "Origin Name should be between 4 an 30 characters");
        validateBadRequest(flightRequestDestinationNameRequiredValidation, "Destination Name is mandatory");
        validateBadRequest(flightRequestDestinationNameLengthValidation, "Destination Name should be between 4 an 30 characters");
    }

    private void validateBadRequest(FlightRequest hotelRequest, String expectedErrorMessage) throws Exception {
        // Prepare request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(flightsUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(hotelRequest));

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