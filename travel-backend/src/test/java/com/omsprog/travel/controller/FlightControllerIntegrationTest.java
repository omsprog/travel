package com.omsprog.travel.controller;

import com.omsprog.travel.dto.response.FlightResponse;
import com.omsprog.travel.util.AeroLine;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Flight can be created")
    void validFlight_whenCreateFlight_returnsCreatedFlight() throws JSONException {
        // Arrange
        JSONObject validFlightRequestJson = new JSONObject();
        validFlightRequestJson.put("originLat", 80.9999);
        validFlightRequestJson.put("originLng", 88.8888);
        validFlightRequestJson.put("destinyLat", 22.2222);
        validFlightRequestJson.put("destinyLng", 11.1111);
        validFlightRequestJson.put("originName", "Mexico");
        validFlightRequestJson.put("destinyName", "Russia");
        validFlightRequestJson.put("price", 90);
        validFlightRequestJson.put("aeroLine", AeroLine.aero_gold.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(validFlightRequestJson.toString(), headers);

        // Act
        ResponseEntity<FlightResponse> createdFlightResponseEntity = testRestTemplate
                .postForEntity("/flights", request, FlightResponse.class);

        FlightResponse createdFlight = createdFlightResponseEntity.getBody();

        if(createdFlight == null)
            fail();

        // Assert
        assertEquals(HttpStatus.OK, createdFlightResponseEntity.getStatusCode());
        assertEquals(validFlightRequestJson.getString("originName"), createdFlight.getOriginName());
    }
}