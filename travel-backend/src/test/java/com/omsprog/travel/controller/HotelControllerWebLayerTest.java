package com.omsprog.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omsprog.travel.dto.request.HotelRequest;
import com.omsprog.travel.dto.response.HotelResponse;
import com.omsprog.travel.error_handler.ErrorsResponse;
import com.omsprog.travel.service.concrete_service.HotelService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = HotelController.class)
@AutoConfigureMockMvc(addFilters = false)
class HotelControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    HotelService hotelService;

    private final static String hotelsUrl = "/hotels";

    static HotelRequest getValidHotelRequest() {
        return HotelRequest.builder()
                .name("Palace")
                .price(BigDecimal.ONE)
                .address("Las Vegas")
                .rating(4)
                .build();
    }

    @Test
    @DisplayName("Hotel can be created")
    void validHotelRequest_whenCreateHotel_returnsCreatedHotel() throws Exception {
        // Arrange
        HotelRequest hotelRequest = getValidHotelRequest();

        HotelResponse hotelResponse = HotelResponse.builder()
                .id(16L)
                .name("Palace")
                .price(BigDecimal.ONE)
                .address("Las Vegas")
                .rating(4)
                .build();

        when(hotelService.create(any(HotelRequest.class)))
                .thenReturn(hotelResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(hotelsUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(hotelRequest));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        HotelResponse createdHotel = new ObjectMapper()
                .readValue(responseBodyAsString, HotelResponse.class);

        // Assert
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Controller returns 200 Status Code");
        assertEquals(hotelRequest.getName(), createdHotel.getName());
    }

    @Test
    @DisplayName("Hotel Controller validation requests")
    void invalidHotelRequests_whenCreateHotel_returns400AndValidationMessages() throws Exception {
        // Arrange
        HotelRequest hotelRequestNameLengthValidation = getValidHotelRequest();
        hotelRequestNameLengthValidation.setName("Pa");
        HotelRequest hotelRequestNameRequiredValidation = getValidHotelRequest();
        hotelRequestNameRequiredValidation.setName(null);

        HotelRequest hotelRequestAddressLengthValidation = getValidHotelRequest();
        hotelRequestAddressLengthValidation.setAddress("La");
        HotelRequest hotelRequestAddressRequiredValidation = getValidHotelRequest();
        hotelRequestAddressRequiredValidation.setAddress(null);

        // Act & Assert
        validateBadRequest(hotelRequestNameLengthValidation, "Hotel name should be between 4 and 30 characters");
        validateBadRequest(hotelRequestNameRequiredValidation, "Hotel name is mandatory");
        validateBadRequest(hotelRequestAddressLengthValidation, "Hotel address should be between 4 and 30 characters");
        validateBadRequest(hotelRequestAddressRequiredValidation, "Hotel address is mandatory");
    }

    private void validateBadRequest(HotelRequest hotelRequest, String expectedErrorMessage) throws Exception {
        // Prepare request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(hotelsUrl)
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