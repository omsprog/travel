package com.omsprog.travel.controller;

import com.omsprog.travel.dto.response.HotelResponse;
import com.omsprog.travel.dto.response.pagination.HotelPage;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Hotel can be created")
    @Order(1)
    void validHotel_whenCreateHotel_returnsCreatedHotel() throws JSONException {
        // Arrange
        JSONObject validHotelRequestJson = new JSONObject();
        validHotelRequestJson.put("name", "Oasis");
        validHotelRequestJson.put("price", 400);
        validHotelRequestJson.put("address", "Miami");
        validHotelRequestJson.put("rating", 4);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(validHotelRequestJson.toString(), headers);

        // Act
        ResponseEntity<HotelResponse> createdHotelResponseEntity = testRestTemplate
                .postForEntity("/hotels", request, HotelResponse.class);

        HotelResponse createdHotel = createdHotelResponseEntity.getBody();

        // Assert
        if(createdHotel == null)
            fail();

        assertEquals(HttpStatus.OK, createdHotelResponseEntity.getStatusCode());
        assertEquals(validHotelRequestJson.getString("name"), createdHotel.getName());
    }

    @Test
    @DisplayName("List of hotels works")
    @Order(2)
    void validGetRequest_whenGetHotels_returnsListOfHotels() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);
        final int pageSize = 5;

        // Act
        ResponseEntity<HotelPage> pageOfFlightsResponseEntity = testRestTemplate
                .exchange(String.format("/hotels?page=0&size=%s", pageSize), HttpMethod.GET, request, HotelPage.class);

        HotelPage pageOfHotels = pageOfFlightsResponseEntity.getBody();

        // Assert
        if(pageOfHotels == null)
            fail();
        assertEquals(HttpStatus.OK, pageOfFlightsResponseEntity.getStatusCode());
        assertEquals(pageSize, pageOfHotels.getContent().size());
        assertFalse(pageOfHotels.isLast());
    }
}