package com.omsprog.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omsprog.travel.dto.request.FlightRequest;
import com.omsprog.travel.dto.response.FlightResponse;
import com.omsprog.travel.error_handler.ErrorsResponse;
import com.omsprog.travel.service.concrete_service.FlightService;
import com.omsprog.travel.util.AeroLine;
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

import java.math.BigDecimal;

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

    static FlightRequest getValidFlightRequest() {
        return FlightRequest.builder()
                .originLat(80.9999)
                .originLng(88.8888)
                .destinyLat(22.2222)
                .destinyLng(11.1111)
                .originName("Mexico")
                .destinyName("Russia")
                .price(BigDecimal.valueOf(90))
                .aeroLine(AeroLine.aero_gold)
                .build();
    }

    @Test
    @DisplayName("Flight can be created")
    void validFlight_whenCreateFlight_returnsCreatedFlight() throws Exception {
        // Arrange
        FlightRequest flightRequest = getValidFlightRequest();

        FlightResponse flightResponse = FlightResponse.builder()
                .id(16L)
                .originLat(80.9999)
                .originLng(88.8888)
                .destinyLat(22.2222)
                .destinyLng(11.1111)
                .originName("Mexico")
                .destinyName("Russia")
                .price(BigDecimal.valueOf(90))
                .aeroLine(AeroLine.aero_gold)
                .build();

        when(flightService.create(any(FlightRequest.class)))
                .thenReturn(flightResponse);

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
        assertEquals(flightRequest.getDestinyLat(), createdFlight.getDestinyLat());
        assertEquals(flightRequest.getDestinyLng(), createdFlight.getDestinyLng());
        assertEquals(flightRequest.getOriginName(), createdFlight.getOriginName());
        assertEquals(flightRequest.getDestinyName(), createdFlight.getDestinyName());
        assertEquals(flightRequest.getPrice(), createdFlight.getPrice());
        assertEquals(flightRequest.getAeroLine(), createdFlight.getAeroLine());
    }

    @Test
    @DisplayName("Origin Name should not be Empty")
    void originNameIsEmpty_whenCreateFlight_returns400AndValidationMessage() throws Exception {
        // Arrange
        FlightRequest flightRequest = getValidFlightRequest();
        flightRequest.setOriginName("");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(flightRequest));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        ErrorsResponse errorsResponse = new ObjectMapper()
                .readValue(responseBodyAsString, ErrorsResponse.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals(1, errorsResponse.getErrors().size());
        assertEquals("Origin Name should be between 4 an 30 characters", errorsResponse.getErrors().get(0));
    }

    @Test
    @DisplayName("Destiny Name should not be Empty")
    void destinyNameIsEmpty_whenCreateFlight_returns400AndValidationMessage() throws Exception {
        // Arrange
        FlightRequest flightRequest = getValidFlightRequest();
        flightRequest.setDestinyName("");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(flightRequest));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        ErrorsResponse errorsResponse = new ObjectMapper()
                .readValue(responseBodyAsString, ErrorsResponse.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals(1, errorsResponse.getErrors().size());
        assertEquals("Destiny Name should be between 4 an 30 characters", errorsResponse.getErrors().get(0));
    }
}